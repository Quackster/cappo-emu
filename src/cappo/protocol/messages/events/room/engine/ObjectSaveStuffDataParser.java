package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import java.util.Map;

public class ObjectSaveStuffDataParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    
    FloorItem floorItem = room.getFloorItem(Main.currentPacket.readInt());
    if (floorItem == null) {
      return;
    }
    MapStuffData data = (MapStuffData)floorItem.extraData;
    
    int size = Main.currentPacket.readInt() / 2;
    for (int i = 0; i < size; i++) {
      data.extraData.put(Main.currentPacket.readString(), Main.currentPacket.readString());
    }
    room.floorItemUpdateNeeded(floorItem);
  }
}


