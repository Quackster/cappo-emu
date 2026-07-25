package cappo.protocol.messages.events.navigator;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.CanCreateEventComposer;

public class CanCreateEventParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (Main.avatar.controllerLevel < 4)) {
      return;
    }
    if (avatar.room.roomData.state != 0)
    {
      QueueWriter.write(Main.socket, CanCreateEventComposer.compose(Boolean.valueOf(false), 3));
      return;
    }
    QueueWriter.write(Main.socket, CanCreateEventComposer.compose(Boolean.valueOf(true), 0));
  }
}


