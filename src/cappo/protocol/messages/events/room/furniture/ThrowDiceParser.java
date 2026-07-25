package cappo.protocol.messages.events.room.furniture;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.protocol.messages.IncomingMessageEvent;

public class ThrowDiceParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    FloorItem item = avatar.room.getFloorItem(Main.currentPacket.readInt());
    if (item == null) {
      return;
    }
    item.baseItem.interactor.OnTriggerFloor(avatar.room, Main, item, 0, false);
  }
}


