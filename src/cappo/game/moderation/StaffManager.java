package cappo.game.moderation;

import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StaffManager
{
  public static final Map<Integer, Connection> staffs = new ConcurrentHashMap(100);
  
  public static void addStaff(int id, Connection cn)
  {
    staffs.put(Integer.valueOf(id), cn);
  }
  
  public static void removeStaff(int id)
  {
    staffs.remove(Integer.valueOf(id));
  }
  
  public static void broadcast(MessageWriter packet)
  {
    for (Connection cn : staffs.values()) {
      QueueWriter.writeAndFlush(cn.socket, packet);
    }
  }
}


