package cappo.game.sound.trax;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Trax
{
  public static Map<Integer, TraxDisc> songDiscs = new HashMap();
  
  public static void Init(DBResult result)
    throws Exception
  {
    songDiscs.clear();
    
    Database.query(result, "SELECT * FROM songs;", new Object[0]);
    while (result.data.next())
    {
      TraxDisc Disc = new TraxDisc(result.data.getInt("id"), result.data.getString("name"), result.data.getString("song_data"), result.data.getInt("length"), result.data.getString("artist"));
      songDiscs.put(Integer.valueOf(Disc.Id), Disc);
    }
  }
}


