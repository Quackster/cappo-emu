package cappo.protocol.messages.composers.inventory.furni;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.protocol.messages.Composer;

public class PostItPlacedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(WallItem item)
  {
    MessageWriter ClientMessage = new MessageWriter(50);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(item.itemId), ClientMessage);
    Composer.add(item.roomDataString(), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


