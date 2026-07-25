package cappo.engine.player;

import cappo.engine.Server;
import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.game.bots.RentalBot;
import cappo.game.collections.Utils;
import cappo.game.pets.Pet;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Clients
{
  private static Map<Integer, PlayerData> clients = new ConcurrentHashMap(
    400, 
    0.8F, 
    8);
  private static Map<String, PlayerData> clientsByName = new ConcurrentHashMap(
    400, 
    0.8F, 
    8);
  private static int onlineUsers;
  
  public static void addPlayerData(PlayerData Client)
  {
    Client.LastUsedThis = Utils.getTimestamp();
    clients.put(Integer.valueOf(Client.userId), Client);
    clientsByName.put(Client.userName, Client);
  }
  
  public static void deleteID(int ClientId)
  {
    PlayerData Client = (PlayerData)clients.remove(Integer.valueOf(ClientId));
    if (Client != null) {
      clientsByName.remove(Client.userName);
    }
  }
  
  public static Map<Integer, PlayerData> GetClients()
  {
    return clients;
  }
  
  public static int GetLoadedCount()
  {
    return clients.size();
  }
  
  public static int GetOnlineCount()
  {
    return onlineUsers;
  }
  
  public static void setOnline(boolean online)
  {
    if (online) {
      onlineUsers += 1;
    } else {
      onlineUsers -= 1;
    }
  }
  
  public static PlayerData getPlayerDataLoaded(int clientId)
  {
    if (clientId < 1) {
      return null;
    }
    PlayerData p = (PlayerData)clients.get(Integer.valueOf(clientId));
    if (p != null) {
      p.LastUsedThis = Utils.getTimestamp();
    }
    return p;
  }
  
  public static PlayerData getPlayerDataLoaded(String clientName)
  {
    if (clientName.isEmpty()) {
      return null;
    }
    PlayerData p = (PlayerData)clientsByName.get(clientName);
    if (p != null) {
      p.LastUsedThis = Utils.getTimestamp();
    }
    return p;
  }
  
  public static PlayerData getPlayerDataFast(int clientId, DBResult result)
  {
    if (clientId < 1) {
      return null;
    }
    PlayerData p = (PlayerData)clients.get(Integer.valueOf(clientId));
    if (p == null) {
      p = getPlayerData(clientId, result);
    }
    if (p != null) {
      p.LastUsedThis = Utils.getTimestamp();
    }
    return p;
  }
  
  public static PlayerData getPlayerData(int clientId)
  {
    if (clientId < 1) {
      return null;
    }
    PlayerData p = (PlayerData)clients.get(Integer.valueOf(clientId));
    if (p == null)
    {
      DBResult result = new DBResult();
      p = getPlayerData(clientId, result);
      result.close();
    }
    if (p != null) {
      p.LastUsedThis = Utils.getTimestamp();
    }
    return p;
  }
  
  public static PlayerData getPlayerData(String UserName)
  {
    if ((UserName == null) || (UserName.isEmpty())) {
      return null;
    }
    PlayerData p = (PlayerData)clientsByName.get(UserName);
    if (p == null)
    {
      DBResult result = new DBResult();
      p = getPlayerData(UserName, result);
      result.close();
    }
    if (p != null) {
      p.LastUsedThis = Utils.getTimestamp();
    }
    return p;
  }
  
  private static PlayerData getPlayerData(String UserName, DBResult result)
  {
    PlayerData p = null;
    try
    {
      Database.query(result, "SELECT user_info.*,users.id,users.rank,users.username,users.real_name,users.mail,users.look,users.achievement_points,users.gender,users.motto,users.account_created FROM users LEFT JOIN user_info ON (users.id=user_info.user_id) WHERE users.username = ? LIMIT 1;", new Object[] {
      









        UserName });
      if (result.data.next())
      {
        p = PlayerData.getPlayer(result.data.getInt("rank"));
        generatePlayerData(result, p);
        addPlayerData(p);
      }
    }
    catch (Exception ex)
    {
      Log.printException("Clients-2", ex);
    }
    return p;
  }
  
  private static PlayerData getPlayerData(int clientId, DBResult result)
  {
    PlayerData p = null;
    try
    {
      Database.query(result, "SELECT user_info.*,users.id,users.rank,users.username,users.real_name,users.mail,users.look,users.achievement_points,users.gender,users.motto,users.account_created FROM users LEFT JOIN user_info ON (users.id=user_info.user_id) WHERE users.id = " + 
      









        clientId + " LIMIT 1;", new Object[0]);
      if (result.data.next())
      {
        p = PlayerData.getPlayer(result.data.getInt("rank"));
        generatePlayerData(result, p);
        addPlayerData(p);
      }
    }
    catch (Exception ex)
    {
      Log.printException("Clients-2", ex);
    }
    return p;
  }
  
  private static void generatePlayerData(DBResult result, PlayerData playerData)
    throws Exception
  {
    playerData.lastVisit = result.data.getInt("login_timestamp");
    playerData.bans = result.data.getInt("bans");
    playerData.cautions = result.data.getInt("cautions");
    playerData.cfhs = result.data.getInt("cfhs");
    playerData.cfhs_abusive = result.data.getInt("cfhs_abusive");
    


    playerData.userId = result.data.getInt("id");
    playerData.userName = result.data.getString("username");
    
    playerData.email = result.data.getString("mail");
    playerData.AchievementsScore = result.data.getInt("achievement_points");
    

    String avatarLook = result.data.getString("look");
    if (!AvatarLook.validateLook(avatarLook)) {
      playerData.avatarLook = new AvatarLook();
    } else {
      playerData.avatarLook = new AvatarLook(avatarLook);
    }
    playerData.sex = (result.data.getString("gender").equals("M") ? 1 : 0);
    playerData.motto = result.data.getString("motto");
    String tmp = result.data.getString("account_created");
    if (tmp.isEmpty())
    {
      playerData.registerDate = Utils.getTimestamp();
      Database.exec("UPDATE users SET account_created = '" + playerData.registerDate + "' WHERE id = " + playerData.userId + ";", new Object[0]);
    }
    else
    {
      if (tmp.contains("-")) {
        tmp = tmp.replace("-", "/");
      }
      if (tmp.contains("/"))
      {
        try
        {
          Date date = Server.date.parse(tmp);
          playerData.registerDate = (date.getTime() / 1000L);
        }
        catch (Exception ex)
        {
          playerData.registerDate = Utils.getTimestamp();
        }
        Database.exec("UPDATE users SET account_created = '" + playerData.registerDate + "' WHERE id = " + playerData.userId + ";", new Object[0]);
      }
      else
      {
        playerData.registerDate = Long.parseLong(tmp);
      }
    }
  }
  
  public static Pet generatePetsData(ResultSet userdata, PlayerData playerData)
    throws Exception
  {
    int type = userdata.getInt("type");
    if ((type < 0) || (type > 27)) {
      return null;
    }
    Pet pet = new Pet(userdata.getInt("id"), userdata.getString("name"), (short)userdata.getInt("type"), (short)Integer.parseInt(userdata.getString("race")), userdata.getString("color"));
    pet.ownerId = playerData.userId;
    pet.ownerName = playerData.userName;
    pet.TimeCreated = userdata.getInt("createstamp");
    pet.Nutrition = userdata.getInt("nutrition");
    pet.Experience = userdata.getInt("expirience");
    pet.Energy = userdata.getInt("energy");
    pet.Respects = userdata.getInt("respect");
    pet.level = 1;
    return pet;
  }
  
  public static RentalBot generateBotsData(ResultSet userdata, PlayerData playerData)
    throws Exception
  {
    int type = userdata.getInt("type");
    if ((type < 0) || (type > 2)) {
      return null;
    }
    RentalBot bot = new RentalBot(userdata.getInt("id"), userdata.getString("name"), (short)userdata.getInt("type"));
    bot.botLook = new AvatarLook(userdata.getString("look"));
    bot.gender = userdata.getString("gender");
    bot.motto = userdata.getString("motto");
    bot.ownerId = playerData.userId;
    bot.ownerName = playerData.userName;
    return bot;
  }
}


