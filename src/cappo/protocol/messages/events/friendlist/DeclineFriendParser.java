package cappo.protocol.messages.events.friendlist;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.MessengerFriendRequest;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;

public class DeclineFriendParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    PlayerData playerData = Main.getPlayerData();
    if (Main.currentPacket.readBoolean())
    {
      playerData.messenger.clearRequests();
      try
      {
        Database.exec("DELETE FROM user_friendreqs WHERE user_id=" + playerData.userId + ";", new Object[0]);
      }
      catch (Exception ex)
      {
        Log.printException("DeclineBuddyParser", ex);
      }
    }
    int count = Main.currentPacket.readInt();
    for (int i = 0; i < count; i++)
    {
      MessengerFriendRequest req = playerData.messenger.pickRequest(Main.currentPacket.readInt());
      if (req != null) {
        try
        {
          Database.exec("DELETE FROM user_friendreqs WHERE user_id=" + playerData.userId + " AND friend_id=" + req.userid + ";", new Object[0]);
        }
        catch (Exception ex)
        {
          Log.printException("DeclineBuddyParser", ex);
        }
      }
    }
  }
}


