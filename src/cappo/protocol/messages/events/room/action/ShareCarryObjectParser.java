package cappo.protocol.messages.events.room.action;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class ShareCarryObjectParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if (avatar.carryItemID < 1) {
      return;
    }
    Avatar clientAvatar = avatar.room.getRoomUserById(Main.currentPacket.readInt());
    if (clientAvatar == null) {
      return;
    }
    clientAvatar.CarryItem(avatar.carryItemID);
    avatar.CarryItem(0);
  }
}


