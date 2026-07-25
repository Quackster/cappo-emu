package cappo.protocol.messages.events.navigator;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RoomTagSearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    String search = Main.currentPacket.readString();
    
    List<Integer> results = new ArrayList();
    Object[] keys = RoomManager.GetRooms().values().toArray();
    for (Object key : keys)
    {
      RoomData currentRoom = (RoomData)key;
      if ((currentRoom != null) && (!results.contains(Integer.valueOf(currentRoom.roomId))))
      {
        for (String tag : currentRoom.tags) {
          if (tag.equals(search))
          {
            results.add(Integer.valueOf(currentRoom.roomId));
            break;
          }
        }
        if (results.size() > 49) {
          break;
        }
      }
    }
    if (results.size() < 50)
    {
      DBResult result = new DBResult();
      try
      {
        Database.query(result, "SELECT DISTINCT id FROM rooms WHERE tags = ? ORDER BY caption DESC LIMIT " + (50 - results.size()) + ";", new Object[] { search });
        while (result.data.next())
        {
          int id = result.data.getInt("id");
          if (!results.contains(Integer.valueOf(id)))
          {
            results.add(Integer.valueOf(id));
            if (results.size() > 49) {
              break;
            }
          }
        }
      }
      catch (Exception ex)
      {
        Log.printException("RoomTagSearchParser-1", ex);
      }
      result.close();
    }
    List<RoomData> roomList = new ArrayList();
    for (Iterator localIterator = results.iterator(); localIterator.hasNext();)
    {
      int RoomId = ((Integer)localIterator.next()).intValue();
      try
      {
        RoomData room = RoomManager.getRoom(RoomId);
        if (room == null)
        {
          room = RoomManager.loadRoom(RoomId);
          if (room == null) {}
        }
        else
        {
          roomList.add(room);
        }
      }
      catch (Exception ex)
      {
        Log.printException("RoomTagSearchParser-2", ex);
      }
    }
    QueueWriter.write(Main.socket, GuestRoomSearchResultComposer.compose(1, search, roomList));
  }
}


