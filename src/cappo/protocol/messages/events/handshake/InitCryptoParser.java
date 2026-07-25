package cappo.protocol.messages.events.handshake;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.handshake.BannerTokenComposer;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

public class InitCryptoParser
  extends IncomingMessageEvent
{
  private static BigInteger prime;
  private static BigInteger generator;
  
  static {}
  
  private static void getPrime()
  {
    SecureRandom random = new SecureRandom();
    do
    {
      prime = BigInteger.probablePrime(200, random);
    } while (!prime.isProbablePrime(10));
    do
    {
      generator = BigInteger.probablePrime(30, random);
    } while (!generator.isProbablePrime(10));
  }
  
  public void messageReceived(Connection Main)
    throws Exception
  {
    String data = prime.toString() + ":" + generator.toString();
    String secret = "!!#!#$#$#%GSdfiaje";
    int Length = "!!#!#$#$#%GSdfiaje".length();
    int magic = data.length();
    byte[] buf = new byte[magic];
    int p = 0;
    for (int i = 0; i < magic; i++)
    {
      buf[i] = ((byte)(data.charAt(i) & 0xFF ^ "!!#!#$#$#%GSdfiaje".charAt(p) & 0xFF ^ magic));
      p++;
      if (p == Length) {
        p = 0;
      }
    }
    Main.InitDH(prime, generator, Main.generateRandomHexString(30));
    
    String encoded = Base64.getEncoder().encodeToString(buf);
    QueueWriter.write(Main.socket, BannerTokenComposer.compose(encoded));
  }
}


