package cappo.protocol.messages.events.friendlist;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.friendlist.HabboSearchResultsComposer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HabboSearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    String Search = Main.currentPacket.readString();
    
    List<Integer> Results = new ArrayList();
    int size = 0;
    
    Object[] keys = Clients.GetClients().values().toArray();
    for (Object key : keys)
    {
      PlayerData Current = (PlayerData)key;
      if (Current != null) {
        if (!Results.contains(Integer.valueOf(Current.userId))) {
          if (Current.userName.contains(Search))
          {
            Results.add(Integer.valueOf(Current.userId));
            
            size++;
            if (size >= 20) {
              break;
            }
          }
        }
      }
    }
    if (size < 20)
    {
      DBResult result = new DBResult();
      try
      {
        Database.query(result, "SELECT id FROM users WHERE username LIKE ? ORDER BY username DESC LIMIT " + (20 - size) + ";", new Object[] { Search + "%" });
        while (result.data.next())
        {
          int id = result.data.getInt("id");
          if (!Results.contains(Integer.valueOf(id))) {
            Results.add(Integer.valueOf(id));
          }
        }
      }
      catch (Exception ex)
      {
        Log.printException("HabboSearchParser", ex);
      }
      result.close();
    }
    List<PlayerData> PlayersFriends = new ArrayList();
    Object Players = new ArrayList(Results.size());
    for (Iterator<Integer> localIterator = Results.iterator(); localIterator.hasNext();)
    {
      int UserId = localIterator.next().intValue();
      try
      {
        PlayerData User = Clients.getPlayerData(UserId);
        if (User != null) {
          if (Main.playerData.messenger.haveFriend(User.userId)) {
            PlayersFriends.add(User);
          } else {
            ((List)Players).add(User);
          }
        }
      }
      catch (Exception ex)
      {
        Log.printException("HabboSearchParser-1", ex);
      }
    }
    QueueWriter.write(Main.socket, HabboSearchResultsComposer.compose(PlayersFriends, (List)Players));
  }
}


