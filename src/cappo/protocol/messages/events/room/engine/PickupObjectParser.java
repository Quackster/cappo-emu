package cappo.protocol.messages.events.room.engine;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.furni.FurniListAddOrUpdateComposer;

public class PickupObjectParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    PlayerData playerData = Main.getPlayerData();
    
    int type = Main.currentPacket.readInt();
    if (type == 2)
    {
      FloorItem flooritem = room.getFloorItem(Main.currentPacket.readInt());
      if (flooritem == null) {
        return;
      }
      if ((flooritem.owner.userId != playerData.userId) && (!playerData.allowEjectFurni())) {
        return;
      }
      room.removeFloorItem(flooritem, playerData.userId);
      
      Main.inventoryAddFloorItem(flooritem);
      flooritem.setMysqlState(2);
      
      QueueWriter.write(Main.socket, FurniListAddOrUpdateComposer.compose(flooritem));
    }
    else if (type == 1)
    {
      WallItem wallitem = room.getWallItem(Main.currentPacket.readInt());
      if (wallitem == null) {
        return;
      }
      if ((wallitem.owner.userId != playerData.userId) && (!playerData.allowEjectFurni())) {
        return;
      }
      room.removeWallItem(wallitem, playerData.userId);
      if (wallitem.baseItem.interactorType == Interactor.InteractorType.postit) {
        return;
      }
      Main.inventoryAddWallItem(wallitem);
      wallitem.setMysqlState(2);
      
      QueueWriter.write(Main.socket, FurniListAddOrUpdateComposer.compose(wallitem));
    }
  }
}
