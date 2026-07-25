package cappo.protocol.messages.events.navigator;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.MessengerFriend;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer;
import java.util.HashMap;
import java.util.Map;

public class RoomsWhereMyFriendsAreSearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    Map<Integer, RoomData> roomList = new HashMap();
    for (MessengerFriend friend : cn.playerData.messenger.getFriends())
    {
      PlayerData friendPlayer = Clients.getPlayerDataLoaded(friend.userId);
      if ((friendPlayer != null) && (friendPlayer.connection != null))
      {
        Avatar avatar = friendPlayer.connection.avatar;
        if (avatar != null)
        {
          RoomTask room = avatar.room;
          if (room != null) {
            roomList.put(Integer.valueOf(room.roomId), room.roomData);
          }
        }
      }
    }
    QueueWriter.write(cn.socket, GuestRoomSearchResultComposer.compose(0, "4", roomList.values()));
  }
}


