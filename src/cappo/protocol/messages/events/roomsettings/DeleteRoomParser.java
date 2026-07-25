package cappo.protocol.messages.events.roomsettings;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.furni.FurniListUpdateComposer;
import java.util.Map;

public class DeleteRoomParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    RoomData roomData = (RoomData)cn.ownRooms.remove(Integer.valueOf(cn.currentPacket.readInt()));
    if (roomData == null) {
      return;
    }
    cn.ownRooms.remove(Integer.valueOf(roomData.roomId));
    
    RoomTask room = roomData.room;
    if (room != null)
    {
      for (FloorItem floorItem : room.FloorItems.values())
      {
        room.removeFloorItem(floorItem, cn.playerData.userId);
        
        cn.inventoryAddFloorItem(floorItem);
        floorItem.setMysqlState(2);
      }
      room.FloorItems.clear();
      for (WallItem wallItem : room.WallItems.values())
      {
        room.removeWallItem(wallItem, cn.playerData.userId);
        if (wallItem.baseItem.interactorType != Interactor.InteractorType.postit)
        {
          cn.inventoryAddWallItem(wallItem);
          wallItem.setMysqlState(2);
        }
      }
      room.WallItems.clear();
      
      QueueWriter.write(cn.socket, FurniListUpdateComposer.compose());
      for (Avatar user : room.userList.values()) {
        room.removeUserFromRoom(user.cn, true, false);
      }
    }
    roomData.setFlag(1, true);
    

    roomData.delete();
  }
}
