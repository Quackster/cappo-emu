package cappo.protocol.messages.events.room.avatar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.action.UserDanceComposer;

public class DanceParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    avatar.idleTime = 0;
    
    int DanceId = Main.currentPacket.readInt();
    if (DanceId != 1) {
      if (DanceId < 0) {
        DanceId = 0;
      } else if (DanceId > 8) {
        DanceId = 0;
      }
    }
    if ((DanceId > 0) && (avatar.carryItemID > 0)) {
      avatar.CarryItem(0);
    }
    avatar.DanceId = DanceId;
    
    avatar.room.sendMessage(UserDanceComposer.compose(avatar.virtualId, avatar.DanceId));
  }
}


