package cappo.game.navigator.officialrooms;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficialRooms
{
  public static final int TAG_SEARCH = 1;
  public static final int ROOM = 2;
  public static final int TAB = 4;
  public static Map<Integer, List<Official>> items = new HashMap();
  public static int SIZE;
  
  public static void init(DBResult result)
    throws Exception
  {
    items.clear();
    SIZE = 0;
    
    Database.query(result, "SELECT * FROM navigator_official WHERE enabled='1' ORDER BY order_id ASC;", new Object[0]);
    while (result.data.next())
    {
      int type = result.data.getInt("type");
      Official item = null;
      if (type == 2)
      {
        item = new OfficialRoom(result.data);
      }
      else if (type == 4)
      {
        item = new OfficialRoomTab(result.data);
      }
      else
      {
        if (type != 1) {
          continue;
        }
        item = new OfficialRoomTagSearch(result.data);
      }
      List<Official> a = (List)items.get(Integer.valueOf(item.parentId));
      if (a == null)
      {
        a = new ArrayList();
        items.put(Integer.valueOf(item.parentId), a);
      }
      a.add(item);
      
      SIZE += 1;
    }
  }
}


