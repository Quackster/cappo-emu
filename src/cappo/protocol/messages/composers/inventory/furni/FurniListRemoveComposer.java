package cappo.protocol.messages.composers.inventory.furni;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class FurniListRemoveComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int itemId)
  {
    MessageWriter ClientMessage = new MessageWriter(20);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(itemId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


