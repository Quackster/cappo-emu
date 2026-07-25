package cappo.protocol.messages.events.room.furniture;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.furniture.RequestSpamWallPostItComposer;

public class OpenPostItParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    GenericWallItem item = (GenericWallItem)avatar.room.getWallItem(Main.currentPacket.readInt());
    if ((item == null) || (item.baseItem.interactorType != Interactor.InteractorType.postit)) {
      return;
    }
    QueueWriter.write(Main.socket, RequestSpamWallPostItComposer.compose(item));
  }
}
