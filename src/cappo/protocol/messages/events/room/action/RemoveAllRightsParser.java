package cappo.protocol.messages.events.room.action;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.settings.PlayerRight;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.permissions.YouAreNotControllerComposer;
import cappo.protocol.messages.composers.roomsettings.FlatControllerRemovedComposer;
import java.util.Map;

public class RemoveAllRightsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (Main.ownRooms.containsKey(Integer.valueOf(avatar.room.roomId)))) {
      return;
    }
    for (PlayerRight right : avatar.room.usersWithRights.values())
    {
      QueueWriter.write(Main.socket, FlatControllerRemovedComposer.compose(avatar.room.roomId, right.player.userId));
      
      Connection clientConnection = right.player.connection;
      if (clientConnection != null)
      {
        Avatar clientAvatar = clientConnection.avatar;
        if (clientAvatar != null) {
          if (clientAvatar.room == avatar.room)
          {
            clientAvatar.controllerLevel = 0;
            QueueWriter.writeAndFlush(clientConnection.socket, YouAreNotControllerComposer.compose());
          }
        }
      }
    }
    avatar.room.usersWithRights.clear();
  }
}


