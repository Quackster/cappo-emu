package cappo.protocol.messages.events.navigator;

import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.DatabaseQueryTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomTextSearchParser
  extends IncomingMessageEvent
{
  private static final int PARAM_1 = 0;
  private static final int PARAM_2 = 1;
  private static Method roomsCallBack;
  
  public void messageReceived(Connection cn)
  {
    String search = cn.currentPacket.readString();
    
    Map<Integer, RoomData> roomList = new HashMap();
    Object[] keys = RoomManager.GetRooms().values().toArray();
    
    int type = 1;
    if (search.startsWith("owner:"))
    {
      search = search.substring(6);
      type = 2;
    }
    for (Object key : keys)
    {
      RoomData currentRoom = (RoomData)key;
      if ((currentRoom != null) && (!roomList.containsKey(Integer.valueOf(currentRoom.roomId)))) {
        if (type == 1 ? 
          !currentRoom.name.contains(search) : 
          


          (type != 2) || 
          (currentRoom.roomOwnerName.startsWith(search)))
        {
          roomList.put(Integer.valueOf(currentRoom.roomId), currentRoom);
          if (roomList.size() >= 50) {
            break;
          }
        }
      }
    }
    Object[] extra = { cn, roomList };
    if (roomList.size() < 50)
    {
      DatabaseQueryTask queryTask;
      if (type == 2)
      {
        queryTask = new DatabaseQueryTask("SELECT * FROM rooms WHERE user_name = ? ORDER BY caption DESC LIMIT " + (50 - roomList.size()) + ";", roomsCallBack, extra, new Object[] { search });
      }
      else
      {
        search = search + "%";
        queryTask = new DatabaseQueryTask("SELECT * FROM rooms WHERE caption LIKE ? OR user_name LIKE ? ORDER BY caption DESC LIMIT " + (50 - roomList.size()) + ";", roomsCallBack, extra, new Object[] { search, search });
      }
      DatabaseQueryTask.addTask(queryTask, 0, 0);
    }
    else
    {
      try
      {
        roomsCallBack(null, extra);
      }
      catch (Exception ex)
      {
        Log.printException("", ex);
      }
    }
  }
  
  public static boolean roomsCallBack(ResultSet result, Object extra)
    throws Exception
  {
    Object[] data = (Object[])extra;
    Connection cn = (Connection)data[0];
    

    Map<Integer, RoomData> roomList = (Map)data[1];
    if (result != null) {
      while (result.next())
      {
        RoomData room = RoomManager.getRoom(result.getInt("id"));
        if (room == null) {
          room = RoomManager.loadRoomResultSet(result);
        }
        if ((room != null) && 
          (!roomList.containsKey(Integer.valueOf(room.roomId)))) {
          roomList.put(Integer.valueOf(room.roomId), room);
        }
      }
    }
    QueueWriter.write(cn.socket, GuestRoomSearchResultComposer.compose(1, "9", roomList.values()));
    
    return true;
  }
  
  static
  {
    try
    {
      roomsCallBack = RoomTextSearchParser.class.getMethod("roomsCallBack", new Class[] { ResultSet.class, Object.class });
    }
    catch (Exception ex)
    {
      Log.printException("", ex);
    }
  }
}


