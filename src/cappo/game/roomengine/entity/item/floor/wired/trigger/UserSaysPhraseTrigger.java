package cappo.game.roomengine.entity.item.floor.wired.trigger;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import cappo.game.roomengine.wired.WiredManager;
import java.sql.ResultSet;
import java.util.List;

public class UserSaysPhraseTrigger
  extends WiredTriggerBase
{
  public String keyword;
  
  public int getCode()
  {
    return 0;
  }
  
  public void setManager(WiredManager manager)
  {
    super.setManager(manager);
    this.wiredManager.triggersUserSays.add(this);
  }
  
  public void removeManager()
  {
    this.wiredManager.triggersUserSays.remove(this);
    super.removeManager();
  }
  
  public boolean launch(Connection playerData, Object extra)
  {
    String sExtra = (String)extra;
    if ((this.keyword != null) && 
      (!sExtra.contains(this.keyword))) {
      return false;
    }
    return super.launch(playerData, null);
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
        this.keyword = result.data.getString("trigger_data");
        if (this.keyword.isEmpty()) {
          this.keyword = null;
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
      this.keyword = data;
    } else {
      this.keyword = null;
    }
  }
  
  public String getWiredData()
  {
    return this.keyword != null ? this.keyword : "";
  }
}


