package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class MoveAvatarParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if (Main.teleport != null) {
      return;
    }
    avatar.idleTime = 0;
    
    int targetX = Main.currentPacket.readInt();
    int targetY = Main.currentPacket.readInt();
    if ((targetX != avatar.x) || (targetY != avatar.y)) {
      avatar.moveTo(targetX, targetY);
    }
  }
}


