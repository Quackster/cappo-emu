package cappo.protocol.messages.events.room.action;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.settings.PlayerRight;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.permissions.YouAreNotControllerComposer;
import cappo.protocol.messages.composers.roomsettings.FlatControllerRemovedComposer;
import java.util.Map;

public class RemoveRightsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    
    int ammount = Main.currentPacket.readInt();
    if ((ammount < 1) || (ammount > 50) || (Main.playerData.userId != room.roomData.roomOwner.userId)) {
      return;
    }
    for (int i = 0; i < ammount; i++)
    {
      PlayerRight right = (PlayerRight)room.usersWithRights.remove(Integer.valueOf(Main.currentPacket.readInt()));
      if (right != null)
      {
        QueueWriter.write(Main.socket, FlatControllerRemovedComposer.compose(room.roomId, right.player.userId));
        
        Connection clientConnection = right.player.connection;
        if (clientConnection != null)
        {
          Avatar clientAvatar = clientConnection.avatar;
          if ((clientAvatar != null) && 
            (clientAvatar.room == room))
          {
            clientAvatar.controllerLevel = 0;
            QueueWriter.writeAndFlush(clientConnection.socket, YouAreNotControllerComposer.compose());
          }
        }
        try
        {
          Database.exec("DELETE FROM room_rights WHERE `user_id`='" + right.player.userId + "' AND `room_id`='" + room.roomId + "';", new Object[0]);
        }
        catch (Exception ex)
        {
          Log.printException("rights", ex);
        }
      }
    }
  }
}


