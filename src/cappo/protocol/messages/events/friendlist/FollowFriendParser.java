package cappo.protocol.messages.events.friendlist;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.friendlist.FollowFriendFailedComposer;
import cappo.protocol.messages.composers.navigator.RoomForwardComposer;

public class FollowFriendParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    int userId = cn.currentPacket.readInt();
    
    PlayerData client = Clients.getPlayerDataLoaded(userId);
    if ((client == null) || (client.connection == null))
    {
      QueueWriter.write(cn.socket, FollowFriendFailedComposer.compose(1));
      return;
    }
    Avatar clientAvatar = client.connection.avatar;
    if (!cn.playerData.messenger.haveFriend(client.userId))
    {
      QueueWriter.write(cn.socket, FollowFriendFailedComposer.compose(0));
      return;
    }
    if (clientAvatar == null)
    {
      QueueWriter.write(cn.socket, FollowFriendFailedComposer.compose(2));
      return;
    }
    RoomTask room = clientAvatar.room;
    if ((cn.avatar != null) && (cn.avatar.room == room)) {
      return;
    }
    QueueWriter.write(cn.socket, RoomForwardComposer.compose(false, room.roomId));
  }
}


