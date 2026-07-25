package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.wired.WiredManager;
import cappo.protocol.messages.IncomingMessageEvent;

public class UseFurnitureParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    
    FloorItem item = room.getFloorItem(Main.currentPacket.readInt());
    if (item == null) {
      return;
    }
    item.baseItem.interactor.OnTriggerFloor(room, Main, item, Main.currentPacket.readInt(), (avatar.controllerLevel == 1) || (avatar.controllerLevel >= 4));
    room.wiredManager.parseWiredMutacion(Main, item);
  }
}


