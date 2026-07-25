package cappo.protocol.messages.events.inventory.furni;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.item.extradata.ExtraData1;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import cappo.game.roomengine.entity.item.extradata.StringArrayStuffData;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.engine.RoomPropertyComposer;

import java.util.List;
import java.util.Map;

public class RequestRoomPropertySetParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || ((avatar.controllerLevel != 1) && 
      (avatar.controllerLevel < 4))) {
      return;
    }
    WallItem wallItem = Main.inventory.getItem(Main.currentPacket.readInt());
    if (wallItem == null) {
      return;
    }
    if (wallItem.baseItem.interactorType != Interactor.InteractorType.roomeffect) {
      return;
    }
    Main.inventoryRemoveItem(wallItem.itemId, true);
    
    RoomTask room = avatar.room;
    if (wallItem.baseItem.itemCategory == 2)
    {
      MapStuffData data = (MapStuffData)wallItem.extraData;
      room.roomData.Wallpaper = ((String)data.extraData.get("state"));
      if (room.roomData.Wallpaper == null) {
        room.roomData.Wallpaper = "0.0";
      }
      room.sendMessage(RoomPropertyComposer.compose("wallpaper", room.roomData.Wallpaper));
    }
    else if (wallItem.baseItem.itemCategory == 4)
    {
      ExtraData1 data = (ExtraData1)wallItem.extraData;
      room.roomData.Landscape = data.value;
      room.sendMessage(RoomPropertyComposer.compose("landscape", room.roomData.Landscape));
    }
    else if (wallItem.baseItem.itemCategory == 3)
    {
      StringArrayStuffData data = (StringArrayStuffData)wallItem.extraData;
      room.roomData.Floor = ((String)data.extraData.get(0));
      room.sendMessage(RoomPropertyComposer.compose("floor", room.roomData.Floor));
    }
  }
}


