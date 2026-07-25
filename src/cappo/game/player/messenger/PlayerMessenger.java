package cappo.game.player.messenger;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.friendlist.FriendsUpdatesComposer;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerMessenger
{
  public PlayerData playerData;
  public static final int FRIENDSLIMIT = 600;
  public static final int FRIENDSLIMIT_VIP = 1200;
  public static final int NONE = 0;
  public static final int HEART = 1;
  public static final int SMILE = 2;
  public static final int BOBBA = 3;
  public Map<Integer, Map<Integer, MessengerFriend>> friendships;
  private Map<Integer, MessengerFriendCategory> categories;
  private Map<Integer, MessengerFriend> friends;
  private Map<Integer, MessengerFriendRequest> requests;
  private Map<Integer, MessengerFriendUpdate> updates;
  public boolean isOnline;
  
  public boolean isFull()
  {
    return friendsCount() >= getLimitFriends();
  }
  
  public int getLimitFriends()
  {
    return 1200;
  }
  
  public PlayerMessenger(PlayerData data)
  {
    this.playerData = data;
  }
  
  public int friendsCount()
  {
    if (this.isOnline) {
      return this.friends.size() + this.requests.size();
    }
    DBResult result = new DBResult();
    try
    {
      Database.query(result, "SELECT (SELECT COUNT(user_id) FROM user_friends WHERE user_id=" + this.playerData.userId + ") AS friendsCount, (SELECT COUNT(user_id) FROM user_friendreqs WHERE user_id=" + this.playerData.userId + ") AS requestsCount;", new Object[0]);
      if (result.data.next())
      {
        int count = result.data.getInt("friendsCount") + result.data.getInt("requestsCount");
        result.close();
        return count;
      }
    }
    catch (Exception ex)
    {
      Log.printException("PlayerMessenger", ex);
      
      result.close();
    }
    return 0;
  }
  
  public MessengerFriend getFriend(int friendid)
  {
    return (MessengerFriend)this.friends.get(Integer.valueOf(friendid));
  }
  
  public Collection<MessengerFriend> getFriends()
  {
    return this.friends.values();
  }
  
  public Collection<MessengerFriendRequest> getFriendRequests()
  {
    return this.requests.values();
  }
  
  public Collection<MessengerFriendCategory> getCategories()
  {
    return this.categories.values();
  }
  
  public void clearRequests()
  {
    this.requests.values();
  }
  
  public void setRelationshipStatus(int type, MessengerFriend friend)
  {
    Map<Integer, MessengerFriend> friendship = (Map)this.friendships.get(Integer.valueOf(type));
    if (friendship == null)
    {
      friendship = new ConcurrentHashMap();
      this.friendships.put(Integer.valueOf(type), friendship);
    }
    friendship.put(Integer.valueOf(friend.userId), friend);
  }
  
  public void removeRelationship(int type, int userId)
  {
    Map<Integer, MessengerFriend> friendship = (Map)this.friendships.get(Integer.valueOf(type));
    if (friendship == null) {
      return;
    }
    friendship.remove(Integer.valueOf(userId));
    if (friendship.isEmpty()) {
      this.friendships.remove(Integer.valueOf(type));
    }
  }
  
  public void initMessenger(DBResult result)
  {
    if (this.isOnline) {
      return;
    }
    this.isOnline = true;
    
    this.friendships = new ConcurrentHashMap();
    
    this.categories = new ConcurrentHashMap();
    this.friends = new ConcurrentHashMap();
    this.requests = new ConcurrentHashMap();
    this.updates = new ConcurrentHashMap();
    
    DBResult result2 = new DBResult();
    try
    {
      Database.query(result, "SELECT * FROM user_friends WHERE user_id = " + this.playerData.userId + ";", new Object[0]);
      while (result.data.next()) {
        addFriend(result.data.getInt("friend_id"), result.data.getInt("type"), result2);
      }
      Database.query(result, "SELECT * FROM user_friendreqs WHERE user_id = " + this.playerData.userId + ";", new Object[0]);
      while (result.data.next()) {
        addFriendRequest(result.data.getInt("friend_id"), result.data.getString("friend_name"), false);
      }
    }
    catch (Exception ex)
    {
      Log.printException("PlayerInventory", ex);
    }
    result2.close();
  }
  
  public void addFriend(PlayerData plr)
  {
    try
    {
      Database.exec("INSERT IGNORE INTO user_friends (user_id,friend_id,type)VALUES(" + this.playerData.userId + "," + plr.userId + ",'0');", new Object[0]);
      Database.exec("INSERT IGNORE INTO user_friends (user_id,friend_id,type)VALUES(" + plr.userId + "," + this.playerData.userId + ",'0');", new Object[0]);
    }
    catch (Exception ex)
    {
      Log.printException("addFriend", ex);
    }
    this.friends.put(Integer.valueOf(plr.userId), new MessengerFriend(plr.userId, 0));
    update(new MessengerFriendUpdate(plr.userId, 1));
    if (plr.messenger.isOnline)
    {
      plr.messenger.friends.put(Integer.valueOf(this.playerData.userId), new MessengerFriend(this.playerData.userId, 0));
      plr.messenger.update(new MessengerFriendUpdate(this.playerData.userId, 1));
      QueueWriter.writeAndFlush(plr.connection.socket, plr.messenger.getFriendUpdstes());
    }
  }
  
  public void addFriend(int friendId)
  {
    PlayerData plr = Clients.getPlayerData(friendId);
    if (plr == null) {
      return;
    }
    try
    {
      Database.exec("INSERT IGNORE INTO user_friends (user_id,friend_id,type)VALUES(" + this.playerData.userId + "," + friendId + ",'0');", new Object[0]);
      Database.exec("INSERT IGNORE INTO user_friends (user_id,friend_id,type)VALUES(" + friendId + "," + this.playerData.userId + ",'0');", new Object[0]);
    }
    catch (Exception ex)
    {
      Log.printException("Disconnect", ex);
    }
    this.friends.put(Integer.valueOf(friendId), new MessengerFriend(friendId, 0));
    update(new MessengerFriendUpdate(friendId, 1));
    if (plr.messenger.isOnline)
    {
      plr.messenger.friends.put(Integer.valueOf(this.playerData.userId), new MessengerFriend(this.playerData.userId, 0));
      plr.messenger.update(new MessengerFriendUpdate(this.playerData.userId, 1));
      QueueWriter.writeAndFlush(plr.connection.socket, plr.messenger.getFriendUpdstes());
    }
  }
  
  public void addFriend(int friendId, int type, DBResult result)
  {
    PlayerData plr = Clients.getPlayerData(friendId);
    if (plr == null) {
      return;
    }
    MessengerFriend friend = new MessengerFriend(friendId, type);
    this.friends.put(Integer.valueOf(friendId), friend);
    if (type != 0) {
      setRelationshipStatus(type, friend);
    }
    if (plr.messenger.isOnline)
    {
      plr.messenger.update(new MessengerFriendUpdate(this.playerData.userId, 0));
      QueueWriter.writeAndFlush(plr.connection.socket, plr.messenger.getFriendUpdstes());
    }
  }
  
  public MessageWriter getFriendUpdstes()
  {
    MessageWriter message = FriendsUpdatesComposer.compose(this.updates.values(), this.playerData);
    this.updates.clear();
    return message;
  }
  
  public void update(MessengerFriendUpdate update)
  {
    this.updates.put(Integer.valueOf(update.userId), update);
  }
  
  public void addFriendRequest(int friendId, String username, boolean needInsert)
  {
    if (this.isOnline)
    {
      this.requests.put(Integer.valueOf(friendId), new MessengerFriendRequest(friendId, username, needInsert));
      return;
    }
    try
    {
      Database.exec("INSERT IGNORE INTO user_friendreqs (user_id,friend_id,friend_name)VALUES(" + this.playerData.userId + "," + friendId + ",?);", new Object[] { username });
    }
    catch (Exception ex)
    {
      Log.printException("Disconnect", ex);
    }
  }
  
  public MessengerFriendRequest pickRequest(int friendId)
  {
    return (MessengerFriendRequest)this.requests.remove(Integer.valueOf(friendId));
  }
  
  public void removeFriend(int friendId)
  {
    MessengerFriend friend = (MessengerFriend)this.friends.remove(Integer.valueOf(friendId));
    if ((friend != null) && (friend.friendType != 0)) {
      removeRelationship(friend.friendType, friendId);
    }
    update(new MessengerFriendUpdate(friendId, -1));
    
    PlayerData friendPlayerData = Clients.getPlayerDataLoaded(friendId);
    if (friendPlayerData == null) {
      return;
    }
    PlayerMessenger friendMessenger = friendPlayerData.messenger;
    if (!friendMessenger.isOnline) {
      return;
    }
    MessengerFriend friend2 = (MessengerFriend)friendMessenger.friends.remove(Integer.valueOf(this.playerData.userId));
    if (friend2 != null)
    {
      if (friend2.friendType != 0) {
        friendMessenger.removeRelationship(friend2.friendType, this.playerData.userId);
      }
      if (friendMessenger.isOnline)
      {
        friendMessenger.update(new MessengerFriendUpdate(this.playerData.userId, -1));
        QueueWriter.writeAndFlush(friendPlayerData.connection.socket, friendMessenger.getFriendUpdstes());
      }
    }
  }
  
  public boolean haveRequest(int friendId)
  {
    return this.requests.containsKey(Integer.valueOf(friendId));
  }
  
  public boolean haveFriend(int friendId)
  {
    return this.friends.containsKey(Integer.valueOf(friendId));
  }
  
  public void save()
  {
    this.isOnline = false;
    

    this.playerData.messenger = new PlayerMessenger(this.playerData);
    try
    {
      if (this.requests != null) {
        for (MessengerFriendRequest req : this.requests.values()) {
          if (req.needInsert)
          {
            req.needInsert = false;
            try
            {
              Database.exec("INSERT IGNORE INTO user_friendreqs (user_id,friend_id,friend_name)VALUES(" + this.playerData.userId + "," + req.userid + ",?);", new Object[] { req.username });
            }
            catch (Exception ex)
            {
              Log.printException("Disconnect", ex);
            }
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("saveFriendRequests", ex);
    }
    try
    {
      if (this.friends != null) {
        for (MessengerFriend friend : this.friends.values())
        {
          PlayerData friendPlayerData = Clients.getPlayerDataLoaded(friend.userId);
          if (friendPlayerData != null)
          {
            PlayerMessenger friendMessenger = friendPlayerData.messenger;
            if (friendMessenger.isOnline)
            {
              friendMessenger.update(new MessengerFriendUpdate(this.playerData.userId, 0));
              QueueWriter.writeAndFlush(friendPlayerData.connection.socket, friendMessenger.getFriendUpdstes());
            }
          }
          if (friend.needUpdate)
          {
            friend.needUpdate = false;
            try
            {
              Database.exec("UPDATE user_friends SET type = '" + friend.friendType + "' WHERE user_id = " + this.playerData.userId + " AND friend_id = " + friend.userId + ";", new Object[0]);
            }
            catch (Exception ex)
            {
              Log.printException("addFriend", ex);
            }
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("saveFriends", ex);
    }
  }
  
  public void serializeRelationshipStatus(MessageWriter clientMessage)
  {
    if (this.isOnline)
    {
      Composer.add(Integer.valueOf(this.friendships.size()), clientMessage);
      for (Iterator localIterator1 = this.friendships.keySet().iterator(); localIterator1.hasNext();)
      {
        int key = ((Integer)localIterator1.next()).intValue();
        Map<Integer, MessengerFriend> friendship = (Map)this.friendships.get(Integer.valueOf(key));
        Composer.add(Integer.valueOf(key), clientMessage);
        Composer.add(Integer.valueOf(friendship.size()), clientMessage);
        Iterator localIterator2 = friendship.values().iterator();
        if (localIterator2.hasNext())
        {
          MessengerFriend friend = (MessengerFriend)localIterator2.next();
          PlayerData friendPlayer = Clients.getPlayerData(friend.userId);
          Composer.add(Integer.valueOf(friendPlayer.userId), clientMessage);
          Composer.add(friendPlayer.userName, clientMessage);
          Composer.add(friendPlayer.avatarLook.toString(), clientMessage);
        }
      }
    }
    else
    {
      DBResult result1 = new DBResult();
      DBResult result2 = new DBResult();
      int i = 0;
      try
      {
        Database.query(result1, "SELECT friend_id,type,count(type) AS typeCount FROM user_friends WHERE user_id = " + this.playerData.userId + " GROUP BY(type);", new Object[0]);
        Composer.add(clientMessage.setSaved(Integer.valueOf(0)), clientMessage);
        while (result1.data.next())
        {
          PlayerData plr = Clients.getPlayerDataFast(result1.data.getInt("friend_id"), result2);
          if (plr != null)
          {
            Composer.add(Integer.valueOf(result1.data.getInt("type")), clientMessage);
            Composer.add(Integer.valueOf(result1.data.getInt("typeCount")), clientMessage);
            Composer.add(Integer.valueOf(plr.userId), clientMessage);
            Composer.add(plr.userName, clientMessage);
            Composer.add(plr.avatarLook.toString(), clientMessage);
            i++;
          }
        }
        clientMessage.writeSaved(Integer.valueOf(i));
      }
      catch (Exception ex)
      {
        Log.printException("PlayerMessenger", ex);
      }
      result1.close();
      result2.close();
    }
  }
}


