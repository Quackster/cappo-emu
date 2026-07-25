package cappo.game.roomengine.entity.item.floor.wired.trigger;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.wired.WiredManager;
import java.sql.ResultSet;
import java.util.List;

public class UserEntersRoomTrigger
  extends WiredTriggerBase
{
  public String filterByName;
  
  public int getCode()
  {
    return 7;
  }
  
  public void setManager(WiredManager manager)
  {
    super.setManager(manager);
    this.wiredManager.triggersEntersRoom.add(this);
  }
  
  public void removeManager()
  {
    this.wiredManager.triggersEntersRoom.remove(this);
    super.removeManager();
  }
  
  public boolean launch(Connection cn, Object extra)
  {
    if ((this.filterByName != null) && 
      (!this.filterByName.equals(cn.playerData.userName))) {
      return false;
    }
    return super.launch(cn, null);
  }
  
  public void saveData()
  {
    try
    {
      Database.exec("INSERT INTO trigger_item (trigger_id,trigger_data)VALUES(" + this.itemId + ",?) on DUPLICATE KEY UPDATE `trigger_data`=?;", new Object[] { getWiredData(), getWiredData() });
      super.saveData();
    }
    catch (Exception ex)
    {
      Log.printException("ShowMessage-saveData", ex);
    }
  }
  
  public void loadData(DBResult result)
  {
    try
    {
      Database.query(result, "SELECT trigger_data FROM trigger_item WHERE trigger_id = " + this.itemId + ";", new Object[0]);
      if (result.data.next())
      {
        this.filterByName = result.data.getString("trigger_data");
        if (this.filterByName.isEmpty()) {
          this.filterByName = null;
        }
      }
      super.loadData(result);
    }
    catch (Exception ex)
    {
      Log.printException("ShowMessage-loadData", ex);
    }
  }
  
  public void setWiredData(String data)
  {
    if (!data.isEmpty()) {
      this.filterByName = data;
    } else {
      this.filterByName = null;
    }
  }
  
  public String getWiredData()
  {
    return this.filterByName != null ? this.filterByName : "";
  }
}


