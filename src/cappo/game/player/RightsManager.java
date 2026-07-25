package cappo.game.player;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import java.lang.reflect.Field;
import java.sql.ResultSet;

public class RightsManager
{
  public static void load(DBResult result)
    throws Exception
  {
    Database.query(result, "SELECT * FROM rights_manager;", new Object[0]);
    while (result.data.next())
    {
      int rank = result.data.getInt("id");
      if ((rank >= 2) && (rank <= 9))
      {
        Class<?> plrClass = PlayerData.securityLevelPlr[rank];
        
        plrClass.getField("allowRoomAlert").setBoolean(null, 
          result.data.getInt("allow_roomalert") == 1);
        

        plrClass.getField("allowPickFurni").setBoolean(null, 
          result.data.getInt("allow_pick") == 1);
        

        plrClass.getField("allowEjectFurni").setBoolean(null, 
          result.data.getInt("allow_eject") == 1);
        

        plrClass.getField("allowRoomControl").setBoolean(null, 
          result.data.getInt("allow_roomcontrol") == 1);
        

        plrClass.getField("allowModTools").setBoolean(null, 
          result.data.getInt("allow_modtools") == 1);
        

        plrClass.getField("allowBan").setBoolean(null, 
          result.data.getInt("allow_ban") == 1);
        

        plrClass.getField("allowGiveBadge").setBoolean(null, 
          result.data.getInt("allow_givebadge") == 1);
        

        plrClass.getField("allowHotelAlert").setBoolean(null, 
          result.data.getInt("allow_ha") == 1);
        

        plrClass.getField("allowGiveMoney").setBoolean(null, 
          result.data.getInt("allow_givemoney") == 1);
      }
    }
  }
}


