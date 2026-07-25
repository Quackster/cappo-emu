package cappo.game.navigator;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class NavigatorCategories
{
  public int id;
  public String caption;
  public int min_rank;
  public static Map<Integer, NavigatorCategories> roomCategories = new HashMap();
  public static int MAX_ID;
  
  public NavigatorCategories(int ID, String name, int minrank)
  {
    this.id = ID;
    this.caption = name;
    this.min_rank = minrank;
  }
  
  public static void Init(DBResult result)
    throws Exception
  {
    Database.query(result, "SELECT * FROM navigator_flatcats WHERE enabled='1';", new Object[0]);
    while (result.data.next())
    {
      NavigatorCategories cat = new NavigatorCategories(result.data.getInt("id"), result.data.getString("caption"), result.data.getInt("min_rank"));
      roomCategories.put(Integer.valueOf(cat.id), cat);
      if (MAX_ID < cat.id) {
        MAX_ID = cat.id;
      }
    }
  }
}


