package cappo.protocol.messages.events.roomsettings;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.roomsettings.FlatControllersComposer;
import java.util.Map;

public class GetFlatControllersParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    Avatar avatar = cn.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    QueueWriter.write(cn.socket, FlatControllersComposer.compose(avatar.room.roomId, avatar.room.usersWithRights.values()));
  }
}


