package cappo.protocol.messages.events.room.furniture;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.game.roomengine.roomevents.HabboWheel_RUN;
import cappo.protocol.messages.IncomingMessageEvent;

public class SpinWheelOfFortuneParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    RoomTask room = avatar.room;
    
    GenericWallItem item = (GenericWallItem)room.getWallItem(Main.currentPacket.readInt());
    if ((item == null) || (item.baseItem.interactorType != Interactor.InteractorType.habbowheel)) {
      return;
    }
    if (item.extraData.equals("-1")) {
      return;
    }
    item.extraData.setExtraData("-1");
    room.wallItemUpdateNeeded(item);
    room.addItemEvent(new HabboWheel_RUN(item), 5);
  }
}
