package cappo.protocol.messages.events.friendlist;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.MessengerFriendRequest;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;

public class AcceptFriendParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    PlayerData playerData = Main.getPlayerData();
    
    int Count = Main.currentPacket.readInt();
    String ids = "";
    DBResult result = new DBResult();
    for (int i = 0; i < Count; i++)
    {
      MessengerFriendRequest req = playerData.messenger.pickRequest(Main.currentPacket.readInt());
      if (req != null)
      {
        playerData.messenger.addFriend(req.userid);
        if (!req.needInsert)
        {
          if (!ids.isEmpty()) {
            ids = ids.concat(" OR ");
          }
          ids = ids.concat("(user_id=" + playerData.userId + " AND friend_id=" + req.userid + ")");
        }
      }
    }
    result.close();
    if (!ids.isEmpty()) {
      try
      {
        Database.exec("DELETE FROM user_friendreqs WHERE " + ids + ";", new Object[0]);
      }
      catch (Exception ex)
      {
        Log.printException("AcceptBuddyParser", ex);
      }
    }
    QueueWriter.write(Main.socket, playerData.messenger.getFriendUpdstes());
  }
}


