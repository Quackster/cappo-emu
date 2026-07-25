package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.protocol.messages.Composer;

public class ItemRemoveComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(WallItem Item, int pickerId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.toString(Item.itemId), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(pickerId), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


