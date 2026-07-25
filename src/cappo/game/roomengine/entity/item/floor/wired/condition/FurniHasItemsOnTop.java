package cappo.game.roomengine.entity.item.floor.wired.condition;

import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import java.util.Map;

public class FurniHasItemsOnTop
  extends WiredConditionBase
{
  private int optionMODE;
  
  public int getCode()
  {
    return 7;
  }
  
  public boolean needUser()
  {
    return false;
  }
  
  public void setWiredOption(int index, int option)
  {
    if (index == 0) {
      this.optionMODE = option;
    }
  }
  
  public int[] getWiredOptions()
  {
    return new int[] { this.optionMODE };
  }
  
  public boolean checkCondition(Connection invoker)
  {
    if (this.optionMODE == 0) {
      return checkFirst();
    }
    return checkAll();
  }
  
  private boolean checkFirst()
  {
    for (FloorItem floorItem : this.items.values()) {
      if (floorItem.itemsOnTop()) {
        return true;
      }
    }
    return false;
  }
  
  private boolean checkAll()
  {
    for (FloorItem floorItem : this.items.values()) {
      if (!floorItem.itemsOnTop()) {
        return false;
      }
    }
    return true;
  }
}


