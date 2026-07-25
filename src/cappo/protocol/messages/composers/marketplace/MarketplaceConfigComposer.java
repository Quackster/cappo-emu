package cappo.protocol.messages.composers.marketplace;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class MarketplaceConfigComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter(50);
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Boolean.valueOf(true), ClientMessage);
      Composer.add(Integer.valueOf(1), ClientMessage);
      Composer.add(Integer.valueOf(1), ClientMessage);
      Composer.add(Integer.valueOf(5), ClientMessage);
      Composer.add(Integer.valueOf(1), ClientMessage);
      Composer.add(Integer.valueOf(10000), ClientMessage);
      Composer.add(Integer.valueOf(48), ClientMessage);
      Composer.add(Integer.valueOf(7), ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


