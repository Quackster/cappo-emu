package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.protocol.messages.Composer;

public class ObjectRemoveComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(FloorItem Item, int pickerId, int delay)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.toString(Item.itemId), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(pickerId), ClientMessage);
    Composer.add(Integer.valueOf(delay), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


