package cappo.protocol.messages.events.room.session;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class QuitParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    Avatar avatar = cn.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    if (room == null) {
      return;
    }
    room.removeUserFromRoom(cn, true, false);
  }
}


