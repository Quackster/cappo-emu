package cappo.protocol.messages.events.room.chat;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class ShoutParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    avatar.idleTime = 0;
    avatar.say(Main.currentPacket.readString(), Main.currentPacket.readInt(), 0, true);
  }
}


