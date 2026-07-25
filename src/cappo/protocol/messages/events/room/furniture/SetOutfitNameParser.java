package cappo.protocol.messages.events.room.furniture;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.OutFitItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class SetOutfitNameParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    OutFitItem floorItem = (OutFitItem)avatar.room.getFloorItem(Main.currentPacket.readInt());
    if (floorItem == null) {
      return;
    }
    floorItem.setName(Main.currentPacket.readString());
    avatar.room.floorItemUpdateNeeded(floorItem);
  }
}


