package cappo.engine;

import cappo.game.collections.Utils;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Issuer for the SSO ticket consumed by
 * {@link cappo.protocol.messages.events.handshake.SSOTicketParser}.
 *
 * The ticket is the base64 of "<timeout>-<x>-<chunk>", where:
 *   timeout = unix seconds after which the ticket expires (must be in the
 *             future when the parser reads it),
 *   x       = any integer (the parser only uses it to rebuild the "public
 *             key" as <timeout> + <x>),
 *   chunk   = "<userId>" XOR-repeated with a "tokenizer" that is itself
 *             {@link Server#ssoSecretKey} XOR-repeated with that public key.
 *
 * Generation is the exact inverse of the parser's decode: the two XOR stages
 * are symmetric, so the same loops reproduce the bytes the parser expects.
 *
 * Charset note: cappo's wire layer ({@link cappo.engine.network.MessageReader}
 * / MessageWriter) treats strings as single bytes via {@code & 0xFF} and
 * {@code new String(bytes)} — i.e. ISO-8859-1 semantics. The parser decodes
 * the ticket with {@code new String(decodedBytes)} (JVM default charset), so
 * for chunk bytes outside ASCII to survive the round trip the server JVM must
 * run with -Dfile.encoding=ISO-8859-1 (as the rest of the wire layer already
 * assumes). This generator uses ISO-8859-1 explicitly so it is lossless and
 * deterministic regardless of the platform default.
 */
public final class SSOTokenizer
{
  /** Default ticket lifetime: 1 hour, in seconds. */
  public static final long DEFAULT_TTL_SECONDS = 3600L;

  private SSOTokenizer() {}

  /**
   * Mint a base64 SSO ticket for the given user id, valid for
   * {@link #DEFAULT_TTL_SECONDS}.
   */
  public static String generate(int userId)
  {
    return generate(userId, DEFAULT_TTL_SECONDS, 0);
  }

  /**
   * Mint a base64 SSO ticket for the given user id, valid for the given
   * number of seconds from now.
   */
  public static String generate(int userId, long ttlSeconds)
  {
    return generate(userId, ttlSeconds, 0);
  }

  /**
   * Mint a base64 SSO ticket.
   *
   * @param userId      the user id embedded in (and recovered from) the ticket.
   * @param ttlSeconds  ticket lifetime in seconds from now; must be > 0.
   * @param x           the opaque integer carried in the ticket's middle
   *                    field; the parser rebuilds the public key as
   *                    <timeout> + x, so any value round-trips. 0 is fine.
   */
  public static String generate(int userId, long ttlSeconds, int x)
  {
    if (ttlSeconds <= 0L) {
      throw new IllegalArgumentException("ttlSeconds must be > 0");
    }
    if (Server.ssoSecretKey == null || Server.ssoSecretKey.isEmpty()) {
      throw new IllegalStateException("Server.ssoSecretKey is not initialised");
    }

    long timeOut = Utils.getTimestamp() + ttlSeconds;
    String pubKey = Long.toString(timeOut + x);

    // tokenizer = ssoSecretKey XOR pubKey (pubKey repeating).
    String sso = Server.ssoSecretKey;
    int len = sso.length();
    int len2 = pubKey.length();
    char[] tokenizer = new char[len];
    int p = 0;
    for (int i = 0; i < len; i++)
    {
      tokenizer[i] = ((char)((sso.charAt(i) & 0xFF) ^ (pubKey.charAt(p) & 0xFF)));
      p++;
      if (p == len2) {
        p = 0;
      }
    }

    // chunk = "<userId>" XOR tokenizer (tokenizer repeating).
    String userIdStr = Integer.toString(userId);
    int lenC = userIdStr.length();
    int len3 = tokenizer.length;
    byte[] chunk = new byte[lenC];
    p = 0;
    for (int i = 0; i < lenC; i++)
    {
      chunk[i] = ((byte)((userIdStr.charAt(i) & 0xFF) ^ (tokenizer[p] & 0xFF)));
      p++;
      if (p == len3) {
        p = 0;
      }
    }

    String token = timeOut + "-" + x + "-" + new String(chunk, StandardCharsets.ISO_8859_1);
    return Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.ISO_8859_1));
  }
}