package cappo.protocol.messages.events.room.chat;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.chat.UserTypingComposer;

public class StartTypingParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (Main.avatar == null) {
      return;
    }
    avatar.room.sendMessage(UserTypingComposer.compose(avatar.virtualId, Boolean.valueOf(true)));
  }
}


