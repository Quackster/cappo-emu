package cappo.protocol.messages.events.room.avatar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class SignParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    int SignId = Main.currentPacket.readInt();
    if ((SignId < 0) || (SignId > 17)) {
      return;
    }
    avatar.idleTime = 0;
    avatar.setStatus("sign", Integer.toString(SignId));
  }
}


