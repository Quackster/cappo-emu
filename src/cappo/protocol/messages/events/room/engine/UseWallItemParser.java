package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.protocol.messages.IncomingMessageEvent;

public class UseWallItemParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    GenericWallItem Item = (GenericWallItem)avatar.room.getWallItem(Main.currentPacket.readInt());
    if (Item == null) {
      return;
    }
    Item.baseItem.interactor.OnTriggerWall(avatar.room, Main, Item, Main.currentPacket.readInt(), (avatar.controllerLevel == 1) || (avatar.controllerLevel >= 4));
  }
}


