package cappo.protocol.messages.events.room.furniture;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BaseItem.FurniLogic;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.OutFitItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class UpdateOutfitParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    FloorItem floorItem = avatar.room.getFloorItem(Main.currentPacket.readInt());
    if ((floorItem == null) || (floorItem.baseItem.logic != BaseItem.FurniLogic.MANNEQUIN)) {
      return;
    }
    ((OutFitItem)floorItem).setLook(Main.getPlayerData());
    avatar.room.floorItemUpdateNeeded(floorItem);
  }
}


