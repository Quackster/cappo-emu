package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ItemAllowGiftComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int ItemId, Boolean AllowGift)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ItemId), ClientMessage);
    Composer.add(AllowGift, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


