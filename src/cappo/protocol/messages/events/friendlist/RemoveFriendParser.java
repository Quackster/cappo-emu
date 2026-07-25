package cappo.protocol.messages.events.friendlist;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;

public class RemoveFriendParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int Count = Main.currentPacket.readInt();
    String ids = "";
    for (int i = 0; i < Count; i++)
    {
      int userId = Main.currentPacket.readInt();
      Main.playerData.messenger.removeFriend(userId);
      if (!ids.isEmpty()) {
        ids = ids.concat(" OR ");
      }
      ids = ids.concat("(user_id=" + Main.playerData.userId + " AND friend_id=" + userId + ") OR (user_id=" + userId + " AND friend_id=" + Main.playerData.userId + ")");
    }
    if (!ids.isEmpty()) {
      try
      {
        Database.exec("DELETE FROM user_friends WHERE " + ids + ";", new Object[0]);
      }
      catch (Exception ex)
      {
        Log.printException("AcceptBuddyParser", ex);
      }
    }
    QueueWriter.write(Main.socket, Main.playerData.messenger.getFriendUpdstes());
  }
}


