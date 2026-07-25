package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeFloorItem;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ObjectsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<FloorItem> FloorItems)
  {
    Map<Integer, String> owners = new HashMap();
    for (FloorItem Item : FloorItems) {
      if (!owners.containsKey(Integer.valueOf(Item.owner.userId))) {
        owners.put(Integer.valueOf(Item.owner.userId), Item.owner.userName);
      }
    }
    MessageWriter writer = new MessageWriter(500 + owners.size() * 50 + FloorItems.size() * 500);
    Composer.initPacket(HEADER, writer);
    Composer.add(Integer.valueOf(owners.size()), writer);
    for (Integer id : owners.keySet())
    {
      Composer.add(id, writer);
      Composer.add(owners.get(id), writer);
    }
    Composer.add(Integer.valueOf(FloorItems.size()), writer);
    for (FloorItem Item : FloorItems) {
      SerializeFloorItem.parse(writer, Item);
    }
    Composer.endPacket(writer);
    
    return writer;
  }
}


