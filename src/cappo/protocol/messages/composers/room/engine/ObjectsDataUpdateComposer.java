package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeItemData;
import java.util.Set;

public class ObjectsDataUpdateComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Set<FloorItem> updateFloorItems)
    throws Exception
  {
    int size = 0;
    MessageWriter ClientMessage = new MessageWriter(100 + updateFloorItems.size() * 100);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(ClientMessage.setSaved(Integer.valueOf(0)), ClientMessage);
    for (FloorItem item : updateFloorItems) {
      if (item.getRoomId() > 0)
      {
        size++;
        Composer.add(Integer.valueOf(item.itemId), ClientMessage);
        SerializeItemData.parse(ClientMessage, item.baseItem, item);
      }
    }
    ClientMessage.writeSaved(Integer.valueOf(size));
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


