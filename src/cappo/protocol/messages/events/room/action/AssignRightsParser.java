package cappo.protocol.messages.events.room.action;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.settings.PlayerRight;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.permissions.YouAreControllerComposer;
import cappo.protocol.messages.composers.roomsettings.FlatControllerAddedComposer;
import java.util.Map;

public class AssignRightsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if (avatar.controllerLevel < 4) {
      return;
    }
    int userId = Main.currentPacket.readInt();
    if (avatar.room.usersWithRights.containsKey(Integer.valueOf(userId))) {
      return;
    }
    PlayerData client = Clients.getPlayerData(userId);
    if (client == null) {
      return;
    }
    avatar.room.usersWithRights.put(Integer.valueOf(client.userId), new PlayerRight(client).needInsert());
    QueueWriter.write(Main.socket, FlatControllerAddedComposer.compose(avatar.room.roomId, client.userId, client.userName));
    if (client.connection == null) {
      return;
    }
    Avatar clientAvatar = client.connection.avatar;
    if ((clientAvatar == null) || (clientAvatar.room.roomId != avatar.room.roomId)) {
      return;
    }
    clientAvatar.controllerLevel = 1;
    QueueWriter.writeAndFlush(client.connection.socket, YouAreControllerComposer.compose(1));
  }
}


