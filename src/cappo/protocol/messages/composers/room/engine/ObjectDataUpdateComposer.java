package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeItemData;

public class ObjectDataUpdateComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(FloorItem Item)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.toString(Item.itemId), ClientMessage);
    SerializeItemData.parse(ClientMessage, Item.baseItem, Item);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


