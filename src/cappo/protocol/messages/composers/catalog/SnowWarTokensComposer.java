package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class SnowWarTokensComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter();
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Integer.valueOf(-1), ClientMessage);
      Composer.add("GET_SNOWWAR_TOKENS", ClientMessage);
      Composer.add(Integer.valueOf(1), ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


