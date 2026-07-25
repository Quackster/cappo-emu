package cappo.game.roomengine.entity.item.floor.wired.effect;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.roomevents.wired.ShowMessageEvent;
import cappo.protocol.messages.composers.room.chat.WhisperComposer;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ShowMessageAction
  extends WiredEffectBase
{
  public String message;
  
  public int getCode()
  {
    return 7;
  }
  
  public boolean needUser()
  {
    return true;
  }
  
  public void invoke(Connection invoker)
  {
    if (this.delayEffect > 0) {
      getRoom().addItemEvent(new ShowMessageEvent(this, invoker), this.delayEffect);
    } else {
      doEffect(this, invoker);
    }
  }
  
  public static void doEffect(ShowMessageAction wired, Connection invoker)
  {
    if (wired.message != null) {
      QueueWriter.writeAndFlush(invoker.socket, WhisperComposer.compose(invoker.avatar.virtualId, wired.message, 0, 0, new ArrayList(), 0));
    }
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
        this.message = result.data.getString("trigger_data");
        if (this.message.isEmpty()) {
          this.message = null;
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
    this.message = data;
  }
  
  public String getWiredData()
  {
    return this.message != null ? this.message : "";
  }
}


