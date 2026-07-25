package cappo.protocol.messages.events.handshake;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.handshake.ServerPublicKeyComposer;
import java.math.BigInteger;

public class GenerateSecretKeyParser
  extends IncomingMessageEvent
{
  private static final BigInteger p = new BigInteger("e81c7e72545e0eeed92a94a7c698d58422787b44b829d1fef60ad8667722e22e07c2194f2c7966b20a65e34b6fc9f34b1989e1fd212f35a83509c1f797fa69fb", 16);
  private static final BigInteger q = new BigInteger("8487dd339b23fe8ff78397f39a7cb17f62517c059738306d8096b1bc74777772ce34d7338d5100453ec0a2b207eed2cc8c63c3df9a1695ad6424d88b4f9d058d", 16);
  private static final BigInteger modulus = p.multiply(q);
  private static final BigInteger pubExp = new BigInteger("10001", 16);
  private static final BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
  private static final BigInteger privExp = pubExp.modInverse(phi);
  private static final int bl = (modulus.bitLength() + 7) / 8;
  
  private static byte[] pkcs1unpad(BigInteger src)
  {
    byte[] b = src.toByteArray();
    
    int i = 0;
    while ((i < b.length) && (b[i] == 0)) {
      i++;
    }
    if ((b.length - i != bl - 1) || (b[i] != 2)) {
      return null;
    }
    i++;
    while (b[i] != 0)
    {
      i++;
      if (i >= b.length) {
        return null;
      }
    }
    byte[] out = new byte[b.length - (i + 1)];
    int p = 0;
    do
    {
      out[p] = b[i];
      p++;i++;
    } while (i < b.length);
    return out;
  }
  
  public void messageReceived(Connection Main)
  {
    String key = Main.currentPacket.readString();
    if ((key.isEmpty()) || (key.length() < 10)) {
      return;
    }
    BigInteger clientKey = new BigInteger(key, 16);
    clientKey = clientKey.modPow(privExp, modulus);
    clientKey = new BigInteger(new String(pkcs1unpad(clientKey)));
    
    // RC4 disabled: the patched client sends/receives plaintext (its cipher
    // process is stubbed to returnvoid). Keep the pubkey reply so the handshake
    // completes, but leave Main.RC4Decode null so the decoder is a passthrough.
    // Main.RC4Decode = new Crypto();
    // Main.RC4Decode.init(Main.HextoBytes(Main.generateSharedKey(clientKey)));
    QueueWriter.write(Main.socket, ServerPublicKeyComposer.compose(Main.getPublicKey()));
  }
}


