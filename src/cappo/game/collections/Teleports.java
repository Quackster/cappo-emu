package cappo.game.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Teleports
{
  private static Map<Integer, Integer> ParentId = new ConcurrentHashMap();
  private static Map<Integer, Integer> RoomId = new ConcurrentHashMap();
  
  public static void delRoom(int Id)
  {
    RoomId.remove(Integer.valueOf(Id));
  }
  
  public static boolean teleLoaded(int Id)
  {
    return RoomId.containsKey(Integer.valueOf(Id));
  }
  
  public static int getRoom(int Id)
  {
    if (RoomId.containsKey(Integer.valueOf(Id))) {
      return ((Integer)RoomId.get(Integer.valueOf(Id))).intValue();
    }
    return -1;
  }
  
  public static int getTele(int Id)
  {
    if (ParentId.containsKey(Integer.valueOf(Id))) {
      return ((Integer)ParentId.get(Integer.valueOf(Id))).intValue();
    }
    return -1;
  }
  
  public static void setParents(int Id1, int Id2)
  {
    ParentId.put(Integer.valueOf(Id1), Integer.valueOf(Id2));
    ParentId.put(Integer.valueOf(Id2), Integer.valueOf(Id1));
  }
  
  public static void setRoom(int Id, int Room_Id)
  {
    RoomId.put(Integer.valueOf(Id), Integer.valueOf(Room_Id));
  }
}


