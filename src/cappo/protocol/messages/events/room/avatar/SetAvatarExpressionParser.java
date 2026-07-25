package cappo.protocol.messages.events.room.avatar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.action.AvatarExpressionComposer;

public class SetAvatarExpressionParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    int expression = Main.currentPacket.readInt();
    if (expression == 5)
    {
      avatar.idleTime = 9999;
    }
    else
    {
      avatar.idleTime = 0;
      avatar.room.sendMessage(AvatarExpressionComposer.compose(avatar.virtualId, expression));
    }
  }
}


