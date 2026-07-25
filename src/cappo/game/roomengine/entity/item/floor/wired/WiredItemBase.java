package cappo.game.roomengine.entity.item.floor.wired;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.threadpools.RoomTask;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.wired.WiredManager;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class WiredItemBase
  extends GenericFloorItem
{
  public Map<Integer, FloorItem> deletedItems;
  public Map<Integer, FloorItem> items = new ConcurrentHashMap();
  public WiredManager wiredManager;
  public int selectionType;
  
  public void setWiredOption(int index, int option) {}
  
  public int[] getWiredOptions()
  {
    return new int[0];
  }
  
  public void setWiredData(String data) {}
  
  public String getWiredData()
  {
    return "";
  }
  
  public void onChildMove(FloorItem item, int xy, Direction8 dir) {}
  
  public void onChildPicked(FloorItem item, int xy, Direction8 dir)
  {
    this.items.remove(Integer.valueOf(item.itemId));
  }
  
  public abstract int getCode();
  
  public abstract boolean needUser();
  
  public void setManager(WiredManager manager)
  {
    this.wiredManager = manager;
  }
  
  public void removeManager()
  {
    this.wiredManager = null;
  }
  
  public void refreshItems()
  {
    this.deletedItems = this.items;
    this.items = new ConcurrentHashMap();
  }
  
  public void cleanDeletedItems()
  {
    this.deletedItems = null;
  }
  
  public boolean addItem(FloorItem item)
  {
    if (item == null) {
      return false;
    }
    if (this.items.containsKey(Integer.valueOf(item.itemId))) {
      return false;
    }
    this.items.put(Integer.valueOf(item.itemId), item);
    
    return (this.deletedItems == null) || (this.deletedItems.remove(Integer.valueOf(item.itemId)) == null);
  }
  
  public boolean removeAllItems()
  {
    for (FloorItem item : this.items.values()) {
      item.removeAttachedWired(this.itemId);
    }
    this.items.clear();
    return true;
  }
  
  public void saveData()
    throws Exception
  {
    Database.exec("DELETE FROM trigger_in_place WHERE original_trigger = " + this.itemId + ";", new Object[0]);
    for (Iterator localIterator = this.items.keySet().iterator(); localIterator.hasNext();)
    {
      int id = ((Integer)localIterator.next()).intValue();
      Database.exec("INSERT IGNORE INTO trigger_in_place (original_trigger,triggers_item) VALUES (" + this.itemId + "," + id + ");", new Object[0]);
    }
  }
  
  public void loadData(DBResult result)
    throws Exception
  {
    Database.query(result, "SELECT triggers_item FROM trigger_in_place WHERE original_trigger = " + this.itemId + ";", new Object[0]);
    while (result.data.next()) {
      addItem(getRoom().getFloorItem(result.data.getInt("triggers_item")));
    }
  }
  
  public void deleteData()
    throws Exception
  {
    Database.exec("DELETE FROM trigger_item WHERE trigger_id = " + this.itemId + ";", new Object[0]);
    Database.exec("DELETE FROM trigger_in_place WHERE original_trigger = " + this.itemId + " OR triggers_item = " + this.itemId + ";", new Object[0]);
  }
}


