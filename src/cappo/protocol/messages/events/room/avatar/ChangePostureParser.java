package cappo.protocol.messages.events.room.avatar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class ChangePostureParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    int posture = Main.currentPacket.readInt();
    if (posture == 1) {
      avatar.setStatus("sit", Float.toString(avatar.z + 0.5F) + " 1");
    } else {
      avatar.setStatus("", "");
    }
  }
}


