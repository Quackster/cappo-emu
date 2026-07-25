package cappo.game.roomengine.entity.item.floor.wired.effect;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.games.snowwar.Direction8;
import cappo.game.rollers.RollerMoveDataObject;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.roomevents.wired.MoveRotateEvent;
import cappo.protocol.messages.composers.room.engine.SlideObjectBundleComposer;
import java.sql.ResultSet;
import java.util.Map;

public class MoveRotateItemAction
  extends WiredEffectBase
{
  private int optionMOVE;
  private int optionROTATE;
  
  public int getCode()
  {
    return 4;
  }
  
  public boolean needUser()
  {
    return false;
  }
  
  public void setWiredOption(int index, int option)
  {
    if (index == 0) {
      this.optionMOVE = option;
    } else if (index == 1) {
      this.optionROTATE = option;
    }
  }
  
  public int[] getWiredOptions()
  {
    return new int[] { this.optionMOVE, this.optionROTATE };
  }
  
  public void invoke(Connection invoker)
  {
    if (this.delayEffect > 0) {
      getRoom().addItemEvent(new MoveRotateEvent(this, invoker), this.delayEffect);
    } else {
      doEffect(this);
    }
  }
  
  public static void doEffect(MoveRotateItemAction wired)
  {
    if (!wired.items.isEmpty()) {
      for (FloorItem item : wired.items.values()) {
        if (item.getRoomId() == wired.getRoomId())
        {
          int newX = item.getX();
          int newY = item.getY();
          if (wired.optionMOVE == 4) {
            newY--;
          } else if (wired.optionMOVE == 5) {
            newX++;
          } else if (wired.optionMOVE == 6) {
            newY++;
          } else if (wired.optionMOVE == 7) {
            newX--;
          } else if (wired.optionMOVE == 2)
          {
            if (Utils.GetRandomNumber(0, 2) == 0) {
              newX++;
            } else {
              newX--;
            }
          }
          else if (wired.optionMOVE == 3)
          {
            if (Utils.GetRandomNumber(0, 2) == 0) {
              newY++;
            } else {
              newY--;
            }
          }
          else if (wired.optionMOVE == 1) {
            if (Utils.GetRandomNumber(0, 2) == 1)
            {
              if (Utils.GetRandomNumber(0, 2) == 0) {
                newX++;
              } else {
                newX--;
              }
            }
            else if (Utils.GetRandomNumber(0, 2) == 0) {
              newY++;
            } else {
              newY--;
            }
          }
          Direction8 newRot = item.getDir();
          if (wired.optionROTATE == 1)
          {
            newRot = newRot.rotateDirection90Degrees(true);
          }
          else if (wired.optionROTATE == 2)
          {
            newRot = newRot.rotateDirection90Degrees(false);
          }
          else if (wired.optionROTATE == 3)
          {
            int rnd = Utils.GetRandomNumber(0, 3);
            if (rnd == 1) {
              newRot = newRot.rotateDirection90Degrees(true);
            } else if (rnd == 2) {
              newRot = newRot.rotateDirection90Degrees(false);
            }
          }
          int fromX = item.getX();int fromY = item.getY();
          RollerMoveDataObject moveData = new RollerMoveDataObject(item);
          
          item.getRoom().setFloorItem(null, item, newX, newY, newRot, false);
          
          item.getRoom().sendMessage(SlideObjectBundleComposer.compose(fromX, fromY, moveData));
        }
      }
    }
  }
  
  public void saveData()
  {
    try
    {
      Database.exec("INSERT INTO trigger_rotation (item_id,movement_status,rotation_status)VALUES(" + this.itemId + "," + this.optionMOVE + "," + this.optionROTATE + ") on DUPLICATE KEY UPDATE `movement_status`=" + this.optionMOVE + ",`rotation_status`=" + this.optionROTATE + ";", new Object[0]);
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
      Database.query(result, "SELECT movement_status,rotation_status FROM trigger_rotation WHERE item_id = " + this.itemId + ";", new Object[0]);
      if (result.data.next())
      {
        this.optionMOVE = result.data.getInt("movement_status");
        this.optionROTATE = result.data.getInt("rotation_status");
      }
      super.loadData(result);
    }
    catch (Exception ex)
    {
      Log.printException("ShowMessage-loadData", ex);
    }
  }
}


