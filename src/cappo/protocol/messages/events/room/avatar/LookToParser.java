package cappo.protocol.messages.events.room.avatar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class LookToParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    int X = Main.currentPacket.readInt();
    int Y = Main.currentPacket.readInt();
    
    avatar.idleTime = 0;
    if ((X != avatar.x) || (Y != avatar.y)) {
      avatar.SetRot(Direction8.getRot(avatar.x, avatar.y, X, Y));
    }
  }
}


