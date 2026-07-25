package cappo.game.roomengine;

import cappo.engine.Server;
import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.games.snowwar.Direction8;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.gamemap.CustomGameMap;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.roomlisting.RoomListing;
import cappo.game.roomengine.settings.ChatSettings;
import cappo.game.roomengine.settings.ModerationPermissions;
import cappo.game.roomengine.settings.TradingSettings;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomManager
{
  private static int ActiveRooms;
  private static Map<String, GameMapBase> models = new ConcurrentHashMap(50);
  private static Map<String, Integer> PopularTags = new ConcurrentHashMap(50);
  private static Map<Integer, RoomData> rooms = new ConcurrentHashMap(500);
  
  public static void Init(DBResult result)
    throws Exception
  {}
  
  public static GameMapBase getModel(String key)
  {
    GameMapBase model = (GameMapBase)models.get(key);
    if (model == null)
    {
      DBResult result = new DBResult();
      model = loadModel(key, result, key.startsWith("custom_"));
      result.close();
    }
    return model;
  }
  
  private static GameMapBase loadModel(String key, DBResult result, boolean custom)
  {
    GameMapBase model = null;
    try
    {
      if (custom) {
        Database.query(result, "SELECT heightmap,base FROM room_custom_models WHERE id = ?;", new Object[] { key });
      } else {
        Database.query(result, "SELECT door_x,door_y,door_z,door_dir,heightmap,club_only FROM room_models WHERE id = ?;", new Object[] { key });
      }
      if (result.data.next())
      {
        if (custom)
        {
          GameMapBase base = getModel(result.data.getString("base"));
          
          model = new CustomGameMap(key, base.doorX, 
            base.doorY, base.doorZ, 
            base.DoorOrientation, false);
          
          CustomGameMap tmp = (CustomGameMap)model;
          tmp.baseName = base.modelName;
        }
        else
        {
          model = new GameMapBase(key, 
            result.data.getInt("door_x"), 
            result.data.getInt("door_y"), 
            result.data.getFloat("door_z"), 
            Direction8.getDirection(result.data.getInt("door_dir")), 
            result.data.getInt("club_only") != 0);
        }
        try
        {
          String map = result.data.getString("heightmap");
          map = map.replace('\r', '\n');
          map = map.replace("\n\n", "\n");
          model.generateModel(map.split("\n"));
          if (!(model instanceof CustomGameMap)) {
            models.put(model.modelName, model);
          }
        }
        catch (Exception ex)
        {
          model = null;
          Log.printException("RoomManager", ex);
        }
      }
      return model;
    }
    catch (Exception ex)
    {
      Log.printException("", ex);
      return model;
    }
  }
  
  public static void AddTag(String Tag)
  {
    int Count = 1;
    if (PopularTags.containsKey(Tag)) {
      Count += ((Integer)PopularTags.get(Tag)).intValue();
    }
    PopularTags.put(Tag, Integer.valueOf(Count));
  }
  
  public static RoomData createRoom(Connection user, String roomName, String modelName)
  {
    GameMapBase model = getModel(modelName);
    if (model == null)
    {
      Utils.AlertFromHotel(user.socket, cappo.game.utils.lang.LangTexts.texts[3]);
      return null;
    }
    if (roomName.length() < 3)
    {
      Utils.AlertFromHotel(user.socket, cappo.game.utils.lang.LangTexts.texts[4]);
      return null;
    }
    RoomData roomData = new RoomData(Server.generateRoomId(), 25);
    roomData.name = roomName;
    roomData.description = "";
    roomData.roomOwner = user.playerData;
    roomData.roomOwnerId = user.playerData.userId;
    roomData.roomOwnerName = user.playerData.userName;
    roomData.model = modelName;
    roomData.icon = new RoomIcon(1, 0, new String[0]);
    roomData.password = "";
    roomData.Wallpaper = "0.0";
    roomData.Floor = "0.0";
    roomData.Landscape = "0.0";
    roomData.tags = new String[0];
    
    roomData.setFlag(2, true);
    roomData.setFlag(8, true);
    
    roomData.modPermissions = new ModerationPermissions(0);
    roomData.tradingSettings = new TradingSettings(2);
    roomData.chatSettings = new ChatSettings(0);
    
    roomData.lastUsedThis = Utils.getTimestamp();
    rooms.put(Integer.valueOf(roomData.roomId), roomData);
    
    user.ownRooms.put(Integer.valueOf(roomData.roomId), roomData);
    try
    {
      Database.exec("INSERT INTO `rooms` (`id`,`model_name`,`caption`,`user_id`,`user_name`,`description`,`category`,`score`,`tags`,`icon_bg`,`icon_fg`,`icon_items`,`password`,`wallpaper`,`floor`,`landscape`,`allow_pets`,`allow_pets_eat`,`allow_walkthrough`,`allow_hidewall`,`wallthickness`,`floorthickness`,`staff_pickup`,`public_ccts`)VALUES(" + roomData.roomId + ",?,?," + roomData.roomOwner.userId + ",?,?," + roomData.category + "," + roomData.rating + ",''," + roomData.icon.backgroundImage + "," + roomData.icon.foregroundImage + ",'',?,?,?,?,'" + (roomData.haveFlag(2) ? 1 : 0) + "','" + (roomData.haveFlag(4) ? 1 : 0) + "','" + (roomData.haveFlag(8) ? 1 : 0) + "','" + (roomData.haveFlag(16) ? 1 : 0) + "'," + roomData.wallAnchor + "," + roomData.floorAnchor + ",'" + (roomData.haveFlag(32) ? 1 : 0) + "','');", new Object[] { model.modelName, roomData.name, roomData.roomOwner.userName, roomData.description, roomData.password, roomData.Wallpaper, roomData.Floor, roomData.Landscape });
    }
    catch (Exception ex)
    {
      Log.printException("Room-3", ex);
    }
    return roomData;
  }
  
  public static int GetActiveCount()
  {
    return ActiveRooms;
  }
  
  public static int GetLoadedCount()
  {
    return rooms.size();
  }
  
  public static RoomData getRoom(int RoomId)
  {
    if (RoomId < 1) {
      return null;
    }
    RoomData room = (RoomData)rooms.get(Integer.valueOf(RoomId));
    if (room != null) {
      room.lastUsedThis = Utils.getTimestamp();
    }
    return room;
  }
  
  public static Map<Integer, RoomData> GetRooms()
  {
    return rooms;
  }
  
  public static Map<String, Integer> GetTags()
  {
    return PopularTags;
  }
  
  public static RoomData loadRoomResultSet(ResultSet roomData)
    throws Exception
  {
    RoomData room = new RoomData(roomData.getInt("id"), roomData.getInt("users_max"));
    room.name = roomData.getString("caption");
    room.description = roomData.getString("description");
    room.roomOwnerId = roomData.getInt("user_id");
    room.roomOwnerName = roomData.getString("user_name");
    if (room.roomOwnerName == null) {
      room.roomOwnerName = "";
    }
    room.state = roomData.getInt("state");
    room.category = roomData.getInt("category");
    
    room.model = roomData.getString("model_name");
    if ((room.model == null) || (room.model.isEmpty())) {
      throw new Exception("roomModel is null: " + roomData.getString("model_name"));
    }
    room.rating = roomData.getInt("score");
    
    room.tags = roomData.getString("tags").split(",");
    for (String tag : room.tags) {
      if (tag.length() > 2) {
        AddTag(tag);
      }
    }
    String Icon_Items = roomData.getString("icon_items").replaceAll(".", ",");
    if (Icon_Items.isEmpty()) {
      room.icon = new RoomIcon(roomData.getInt("icon_bg"), roomData.getInt("icon_fg"), new String[0]);
    } else {
      room.icon = new RoomIcon(roomData.getInt("icon_bg"), roomData.getInt("icon_fg"), Icon_Items.split("|"));
    }
    room.password = roomData.getString("password");
    room.Wallpaper = roomData.getString("wallpaper");
    room.Floor = roomData.getString("floor");
    room.Landscape = roomData.getString("landscape");
    room.setFlag(2, roomData.getString("allow_pets").equals("1"));
    room.setFlag(4, roomData.getString("allow_pets_eat").equals("1"));
    room.setFlag(8, roomData.getString("allow_walkthrough").equals("1"));
    room.setFlag(16, roomData.getString("allow_hidewall").equals("1"));
    room.floorAnchor = roomData.getShort("floorthickness");
    room.wallAnchor = roomData.getShort("wallthickness");
    


    room.modPermissions = new ModerationPermissions(roomData.getInt("settings_mod"));
    room.tradingSettings = new TradingSettings(roomData.getInt("settings_trd"));
    room.chatSettings = new ChatSettings(roomData.getInt("settings_chat"));
    
    room.lastUsedThis = Utils.getTimestamp();
    rooms.put(Integer.valueOf(room.roomId), room);
    
    return room;
  }
  
  public static RoomData loadRoom(int roomId)
    throws Exception
  {
    if (roomId < 1) {
      return null;
    }
    DBResult result = new DBResult();
    try
    {
      Database.query(result, "SELECT * FROM rooms WHERE id = '" + roomId + "';", new Object[0]);
      
      RoomData room = null;
      if (result.data.next()) {
        room = loadRoomResultSet(result.data);
      }
      result.close();
      
      return room;
    }
    catch (Exception ex)
    {
      result.close();
      throw ex;
    }
  }
  
  public static void RemoveTag(String Tag)
  {
    if (PopularTags.containsKey(Tag))
    {
      int Count = ((Integer)PopularTags.get(Tag)).intValue() - 1;
      if (Count > 0) {
        PopularTags.put(Tag, Integer.valueOf(Count));
      } else {
        PopularTags.remove(Tag);
      }
    }
  }
  
  public static void setActive()
  {
    ActiveRooms += 1;
  }
  
  public static void setInactive(RoomData roomData)
  {
    ActiveRooms -= 1;
    roomData.room = null;
  }
  
  public static void unloadRoom(int RoomId)
  {
    rooms.remove(Integer.valueOf(RoomId));
  }
}


