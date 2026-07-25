package cappo.game.roomengine.entity.item.floor.wired.trigger;

import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.wired.WiredManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUsesItemTrigger
  extends WiredTriggerBase
{
  public int getCode()
  {
    return 1;
  }
  
  public boolean addItem(FloorItem item)
  {
    if (super.addItem(item))
    {
      List<WiredTriggerBase> triggerList = (List)this.wiredManager.triggersSateChanged.get(Integer.valueOf(item.itemId));
      if (triggerList == null)
      {
        triggerList = new ArrayList();
        this.wiredManager.triggersSateChanged.put(Integer.valueOf(item.itemId), triggerList);
      }
      triggerList.add(this);
      return true;
    }
    return false;
  }
  
  public boolean removeAllItems()
  {
    for (FloorItem item : this.items.values()) {
      removeItem(item);
    }
    super.removeAllItems();
    return true;
  }
  
  public void cleanDeletedItems()
  {
    for (FloorItem item : this.deletedItems.values()) {
      removeItem(item);
    }
    super.cleanDeletedItems();
  }
  
  private void removeItem(FloorItem item)
  {
    List<WiredTriggerBase> triggerList = (List)this.wiredManager.triggersSateChanged.get(Integer.valueOf(item.itemId));
    if (triggerList != null)
    {
      triggerList.remove(this);
      if (triggerList.isEmpty()) {
        this.wiredManager.triggersSateChanged.remove(Integer.valueOf(item.itemId));
      }
    }
  }
}


