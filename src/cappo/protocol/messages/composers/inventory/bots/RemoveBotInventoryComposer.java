package cappo.protocol.messages.composers.inventory.bots;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RemoveBotInventoryComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int botId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(botId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


