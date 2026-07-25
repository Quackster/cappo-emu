package cappo.protocol.messages.events.room.engine;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BaseItem.FurniLogic;
import cappo.game.collections.Teleports;
import cappo.game.games.snowwar.Direction8;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.item.wall.RoomWallItemData;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.handshake.GenericErrorComposer;
import cappo.protocol.messages.composers.room.engine.PlaceObjectErrorComposer;

public class PlaceObjectParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if ((avatar.controllerLevel != 1) && 
      (avatar.controllerLevel < 4))
    {
      QueueWriter.write(Main.socket, GenericErrorComposer.compose(-32000));
      return;
    }
    String PlacementData = Main.currentPacket.readString();
    String[] DataBits = PlacementData.split(" ");
    if (DataBits.length < 3) {
      return;
    }
    int ItemId = Integer.parseInt(DataBits[0]);
    if (DataBits[1].startsWith(":"))
    {
      WallItem item = Main.inventory.getItem(ItemId);
      if (item == null) {
        return;
      }
      if (!(item instanceof GenericWallItem)) {
        return;
      }
      if ((item.baseItem.logic == BaseItem.FurniLogic.ROOMDIMMER) && 
        (avatar.room.MoodlightData != null)) {
        return;
      }
      String[] widD = DataBits[1].substring(3).split(",");
      int widthX = Integer.parseInt(widD[0]);
      int widthY = Integer.parseInt(widD[1]);
      if ((widthX < 0) || (widthY < 0) || (widthX > 200) || (widthY > 200))
      {
        QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11));
        return;
      }
      String[] lenD = DataBits[2].substring(2).split(",");
      int lengthX = Integer.parseInt(lenD[0]);
      int lengthY = Integer.parseInt(lenD[1]);
      if ((lengthX < 0) || (lengthY < 0) || (lengthX > 200) || (lengthY > 200))
      {
        QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11)); return;
      }
      char side;
      if (DataBits[3].equals("r")) {
        side = 'r';
      } else {
        side = 'l';
      }
      item.setRoomData(new RoomWallItemData(avatar.room, item, side, widthX, widthY, lengthX, lengthY));
      if ((item.baseItem.logic == BaseItem.FurniLogic.ROOMDIMMER) && 
        (avatar.room.MoodlightData != null))
      {
        QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11));
        return;
      }
      if (avatar.room.setWallItem(Main, (GenericWallItem)item, true)) {
        Main.inventoryRemoveItem(ItemId, true);
      } else {
        item.cleanRoomData();
      }
    }
    else
    {
      FloorItem item = Main.inventory.getObject(ItemId);
      if (item == null) {
        return;
      }
      item.setRoomData(new RoomFloorItemData(avatar.room, item));
      
      int X = Short.parseShort(DataBits[1]);
      int Y = Short.parseShort(DataBits[2]);
      Direction8 Rot = Direction8.getDirection(Integer.parseInt(DataBits[3]));
      if (avatar.room.setFloorItem(Main, item, X, Y, Rot, true))
      {
        if (item.baseItem.interactorType == Interactor.InteractorType.teleport) {
          Teleports.setRoom(item.itemId, avatar.room.roomId);
        }
        Main.inventoryRemoveItem(ItemId, false);
      }
      else
      {
        item.cleanRoomData();
        
        QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11));
      }
    }
  }
}
