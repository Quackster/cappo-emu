package cappo.protocol.messages.events.handshake;

import cappo.engine.Server;
import cappo.engine.ServerTasks;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.DatabaseQueryTask;
import cappo.game.achievements.UserAchievementManager;
import cappo.game.collections.Badge;
import cappo.game.collections.FavRoom;
import cappo.game.collections.Utils;
import cappo.game.collections.Wardrobe;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.handshake.AuthOKComposer;
import cappo.protocol.messages.composers.handshake.GenericErrorComposer;
import cappo.protocol.messages.composers.handshake.UserDisconnectComposer;
import cappo.protocol.messages.composers.navigator.FavouritesComposer;
import cappo.protocol.messages.composers.navigator.NavigatorSettingsComposer;
import cappo.protocol.messages.composers.notifications.MOTDComposer;
import io.netty.channel.Channel;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Base64;

public class SSOTicketParser
  extends IncomingMessageEvent
{
  public static final Map<Integer, Integer> temporallyBans = new ConcurrentHashMap();
  private static int bansCleaner = 0;
  private static final int QUERY_COUNT = 7;
  private static final int PARAM_1 = 0;
  private static final int PARAM_2 = 1;
  private static Method checkBannedCallBack;
  private static Method avatarCallBack;
  private static Method favRoomsCallBack;
  private static Method ignoredUsersCallBack;
  private static Method wardrobeCallBack;
  private static Method badgesCallBack;
  private static Method roomsCallBack;
  
  public void messageReceived(Connection cn)
  {
    Base64.Decoder decoder = Base64.getMimeDecoder();
    String token = "";
    try
    {
      token = new String(decoder.decode(cn.currentPacket.readString()));
    }
    catch (Exception ex)
    {
      QueueWriter.write(cn.socket, GenericErrorComposer.compose(-400));
      Log.printException("SSOTicketParser", ex);
      return;
    }
    Log.printLog("Token <" + token + ">");
    
    String[] parts = token.split("-", 3);
    if (parts.length != 3)
    {
      QueueWriter.write(cn.socket, GenericErrorComposer.compose(-400));
      return;
    }
    long timeOut = Long.parseLong(parts[0]);
    if (Utils.getTimestamp() > timeOut)
    {
      QueueWriter.write(cn.socket, GenericErrorComposer.compose(-400));
      return;
    }
    String pubKey = Long.toString(timeOut + Integer.parseInt(parts[1]));
    String chunk = parts[2];
    
    int p = 0;
    int len = Server.ssoSecretKey.length();
    int len2 = pubKey.length();
    String tokenizer = "";
    for (int i = 0; i < len; i++)
    {
      tokenizer = tokenizer + (char)(Server.ssoSecretKey.charAt(i) & 0xFF ^ pubKey.charAt(p) & 0xFF);
      p++;
      if (p == len2) {
        p = 0;
      }
    }
    len = chunk.length();
    int len3 = tokenizer.length();
    byte[] buf = new byte[len];
    p = 0;
    for (int i = 0; i < len; i++)
    {
      buf[i] = ((byte)(chunk.charAt(i) & 0xFF ^ tokenizer.charAt(p) & 0xFF));
      p++;
      if (p == len3) {
        p = 0;
      }
    }
    int userId = Integer.parseInt(new String(buf));
    
    Log.printLog("Token-UseId <" + userId + "> " + System.currentTimeMillis());
    try
    {
      PlayerData playerData = Clients.getPlayerData(userId);
      if (playerData == null)
      {
        QueueWriter.write(cn.socket, GenericErrorComposer.compose(-400));
        return;
      }
      if (playerData.connection != null)
      {
        QueueWriter.writeAndClose(cn.socket, UserDisconnectComposer.compose(2));
        if (cn.haveFlag(2))
        {
          cn.setFlag(2, false);
          QueueWriter.write(playerData.connection.socket, ServerTasks.PingMessage);
        }
        else
        {
          playerData.connection.socket.close();
        }
        return;
      }
      DatabaseQueryTask queryTask = new DatabaseQueryTask(7);
      
      Integer tmpBan = (Integer)temporallyBans.get(Integer.valueOf(playerData.userId));
      if (tmpBan != null)
      {
        if (tmpBan.intValue() < Utils.getTimestamp()) {
          temporallyBans.remove(Integer.valueOf(playerData.userId));
        } else {
          QueueWriter.writeAndClose(cn.socket, UserDisconnectComposer.compose(1));
        }
      }
      else if (bansCleaner++ % 50 == 30) {
        for (Iterator localIterator = temporallyBans.keySet().iterator(); localIterator.hasNext();)
        {
          int key = ((Integer)localIterator.next()).intValue();
          Integer timeout = (Integer)temporallyBans.get(Integer.valueOf(key));
          if (timeout.intValue() < Utils.getTimestamp())
          {
            temporallyBans.remove(Integer.valueOf(key));
            break;
          }
        }
      }
      queryTask.addQuery("SELECT type,hours,created FROM bans WHERE user_id = " + 
      


        playerData.userId + " LIMIT 1;", checkBannedCallBack, new Object[] { cn, playerData }, new Object[0]);
      
      queryTask.addQuery("SELECT credits,crystals,activity_points,activity_points_lastupdate,vip_points,home_room,respects,daily_respect_points,daily_pet_respect_points,newbie_status,block_newfriends,block_trade FROM users  WHERE id = " + 
      











        playerData.userId + " LIMIT 1;", avatarCallBack, new Object[] { cn }, new Object[0]);
      
      queryTask.addQuery("SELECT DISTINCT * FROM rooms JOIN user_favorites ON (user_favorites.room_id = rooms.id) WHERE user_favorites.user_id = " + playerData.userId + " LIMIT 30;", favRoomsCallBack, new Object[] { cn }, new Object[0]);
      queryTask.addQuery("SELECT * FROM user_ignores WHERE user_id = " + playerData.userId + ";", ignoredUsersCallBack, new Object[] { cn }, new Object[0]);
      queryTask.addQuery("SELECT * FROM user_wardrobe WHERE user_id = " + playerData.userId + ";", wardrobeCallBack, new Object[] { cn }, new Object[0]);
      queryTask.addQuery("SELECT * FROM user_badges WHERE user_id = " + playerData.userId + ";", badgesCallBack, new Object[] { cn }, new Object[0]);
      queryTask.addQuery("SELECT DISTINCT * FROM rooms WHERE user_id=" + playerData.userId + " LIMIT 200;", roomsCallBack, new Object[] { cn }, new Object[0]);
      DatabaseQueryTask.addTask(queryTask, 0, 0);
    }
    catch (Exception ex)
    {
      Log.printException("SSOTicketParser-1", ex);
      QueueWriter.write(cn.socket, GenericErrorComposer.compose(-400));
    }
  }
  
  public static boolean checkBannedCallBack(ResultSet result, Object extra)
    throws Exception
  {
    Object[] data = (Object[])extra;
    Connection cn = (Connection)data[0];
    PlayerData playerData = (PlayerData)data[1];
    if (result.next())
    {
      result.getString("type");
      int hours = result.getInt("hours");
      long created = result.getLong("created");
      if (hours == 100000)
      {
        QueueWriter.writeAndClose(cn.socket, UserDisconnectComposer.compose(10));
        return false;
      }
      long expire = created + hours * 3600;
      if (expire > Utils.getTimestamp())
      {
        QueueWriter.writeAndClose(cn.socket, UserDisconnectComposer.compose(1));
        return false;
      }
      try
      {
        Database.exec("DELETE FROM bans WHERE user_id =" + playerData.userId + ";", new Object[0]);
      }
      catch (Exception ex)
      {
        Log.printException("Disconnect", ex);
      }
    }
    cn.setPlayerData(playerData);
    

    playerData.lastVisit = Utils.getTimestamp();
    
    return true;
  }
  
  public static boolean avatarCallBack(ResultSet result, Object extra)
    throws Exception
  {
    Object[] data = (Object[])extra;
    Connection cn = (Connection)data[0];
    if (!result.next()) {
      return false;
    }
    cn.credits = result.getInt("credits");
    cn.diamondAmmount = result.getInt("crystals");
    cn.pixelAmmount = result.getInt("activity_points");
    cn.nextPixelsUpdate = result.getLong("activity_points_lastupdate");
    cn.vipPoins = result.getInt("vip_points");
    cn.homeRoom = result.getInt("home_room");
    cn.respects = result.getInt("respects");
    cn.dailyRespectPoints = result.getInt("daily_respect_points");
    cn.dailyPetRespectPoints = result.getInt("daily_pet_respect_points");
    cn.setFlag(4, result.getInt("newbie_status") == 1);
    cn.setFlag(1, false);
    cn.setFlag(16, result.getInt("block_newfriends") == 1);
    cn.setFlag(8, result.getInt("block_trade") == 1);
    
    cn.MaxRooms = 100;
    














    return true;
  }
  
  public static boolean favRoomsCallBack(ResultSet result, Object extra)
    throws Exception
  {
    Object[] data = (Object[])extra;
    Connection cn = (Connection)data[0];
    while (result.next())
    {
      RoomData room = RoomManager.getRoom(result.getInt("id"));
      if (room == null) {
        room = RoomManager.loadRoomResultSet(result);
      }
      if (room != null) {
        cn.favoriteRooms.put(Integer.valueOf(room.roomId), new FavRoom(room));
      }
    }
    return true;
  }
  
  public static boolean ignoredUsersCallBack(ResultSet result, Object extra)
    throws Exception
  {
    Object[] data = (Object[])extra;
    Connection cn = (Connection)data[0];
    while (result.next()) {
      cn.ignoreUsers.add(Integer.valueOf(result.getInt("ignore_id")));
    }
    return true;
  }
  
  public static boolean wardrobeCallBack(ResultSet result, Object extra)
    throws Exception
  {
    Object[] data = (Object[])extra;
    Connection cn = (Connection)data[0];
    while (result.next())
    {
      Wardrobe wrb = new Wardrobe(result.getInt("slot_id"), result.getString("look"), (short)(result.getString("gender").equals("M") ? 1 : 0));
      cn.Wardrobes.put(Short.valueOf(wrb.slotId), wrb);
    }
    return true;
  }
  
  public static boolean badgesCallBack(ResultSet result, Object extra)
    throws Exception
  {
    Object[] data = (Object[])extra;
    Connection cn = (Connection)data[0];
    while (result.next())
    {
      Badge badge = new Badge(result.getInt("id"), result.getString("badge_id"), result.getInt("badge_slot"));
      cn.badges.put(badge.badgeCode, badge);
      if (badge.badgeSlot > 0)
      {
        Badge prev = (Badge)cn.badgesSelected.put(Integer.valueOf(badge.badgeSlot), badge);
        if (prev != null)
        {
          prev.badgeSlot = 0;
          prev.needInsert = true;
        }
      }
    }
    return true;
  }
  
  public static boolean roomsCallBack(ResultSet result, Object extra)
    throws Exception
  {
    Object[] data = (Object[])extra;
    Connection cn = (Connection)data[0];
    while (result.next()) {
      try
      {
        RoomData room = RoomManager.getRoom(result.getInt("id"));
        if (room == null) {
          room = RoomManager.loadRoomResultSet(result);
        }
        if (room != null) {
          cn.ownRooms.put(Integer.valueOf(room.roomId), room);
        }
      }
      catch (Exception ex)
      {
        Log.printException("", ex);
      }
    }
    cn.setFlag(2, true);
    Clients.setOnline(true);
    

    cn.avatarData.achievementManager.fillAchievements();
    

    QueueWriter.write(cn.socket, AuthOKComposer.compose());
    QueueWriter.write(cn.socket, NavigatorSettingsComposer.compose(cn.homeRoom, 0));
    QueueWriter.write(cn.socket, FavouritesComposer.compose(cn.favoriteRooms.keySet()));
    
    cn.getPlayerData().setupLevelStuff();
    if (!cappo.game.utils.lang.LangTexts.texts[5].isEmpty()) {
      QueueWriter.write(cn.socket, MOTDComposer.compose(new String[] { cappo.game.utils.lang.LangTexts.texts[5] }));
    }
    cn.socket.flush();
    
    return true;
  }
  
  static
  {
    try
    {
      checkBannedCallBack = SSOTicketParser.class.getMethod("checkBannedCallBack", new Class[] { ResultSet.class, Object.class });
      avatarCallBack = SSOTicketParser.class.getMethod("avatarCallBack", new Class[] { ResultSet.class, Object.class });
      favRoomsCallBack = SSOTicketParser.class.getMethod("favRoomsCallBack", new Class[] { ResultSet.class, Object.class });
      ignoredUsersCallBack = SSOTicketParser.class.getMethod("ignoredUsersCallBack", new Class[] { ResultSet.class, Object.class });
      wardrobeCallBack = SSOTicketParser.class.getMethod("wardrobeCallBack", new Class[] { ResultSet.class, Object.class });
      badgesCallBack = SSOTicketParser.class.getMethod("badgesCallBack", new Class[] { ResultSet.class, Object.class });
      roomsCallBack = SSOTicketParser.class.getMethod("roomsCallBack", new Class[] { ResultSet.class, Object.class });
    }
    catch (Exception ex)
    {
      Log.printException("", ex);
    }
  }
}


