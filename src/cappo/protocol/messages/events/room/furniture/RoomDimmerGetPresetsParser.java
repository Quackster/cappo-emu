package cappo.protocol.messages.events.room.furniture;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.furniture.RoomDimmerPresetsComposer;

public class RoomDimmerGetPresetsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4) || 
      (avatar.room.MoodlightData == null)) {
      return;
    }
    QueueWriter.write(Main.socket, RoomDimmerPresetsComposer.compose(avatar.room.MoodlightData));
  }
}


