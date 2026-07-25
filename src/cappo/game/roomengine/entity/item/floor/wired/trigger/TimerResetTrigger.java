package cappo.game.roomengine.entity.item.floor.wired.trigger;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.roomevents.wired.TimerSetTimeEvent;
import cappo.game.roomengine.wired.WiredManager;
import java.sql.ResultSet;
import java.util.List;

public class TimerResetTrigger
  extends WiredTriggerBase
{
  public TimerSetTimeEvent event;
  public int delay;
  
  public int getCode()
  {
    return 3;
  }
  
  public void setManager(WiredManager manager)
  {
    super.setManager(manager);
    this.wiredManager.triggersTimers.add(this);
  }
  
  public void removeManager()
  {
    this.wiredManager.triggersTimers.remove(this);
    super.removeManager();
  }
  
  public void setWiredOption(int index, int option)
  {
    if (index == 0) {
      this.delay = option;
    }
  }
  
  public int[] getWiredOptions()
  {
    return new int[] { this.delay };
  }
  
  public static void doTrigger(TimerResetTrigger wired, Connection invoker)
  {
    wired.launch(invoker, Boolean.valueOf(true));
  }
  
  public boolean launch(Connection cn, Object extra)
  {
    if (extra != null) {
      super.launch(cn, null);
    } else if ((this.delay > 0) && (this.event == null)) {
      getRoom().addItemEvent(this.event = new TimerSetTimeEvent(this, cn), this.delay);
    }
    return true;
  }
  
  public void saveData()
  {
    try
    {
      Database.exec("INSERT INTO trigger_item (trigger_id,trigger_data)VALUES(" + this.itemId + ",'" + this.delay + "') on DUPLICATE KEY UPDATE `trigger_data`='" + this.delay + "';", new Object[0]);
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
      if (result.data.next()) {
        this.delay = result.data.getInt("trigger_data");
      }
      super.loadData(result);
    }
    catch (Exception ex)
    {
      Log.printException("ShowMessage-loadData", ex);
    }
  }
}


