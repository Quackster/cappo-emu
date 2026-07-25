package cappo.game.roomengine.entity.item.floor.wired.trigger;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData;

import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData.AffectedTile;
import cappo.game.roomengine.wired.WiredManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserStepsOffItemTrigger
  extends WiredTriggerBase
{
  public int getCode()
  {
    return 1;
  }
  
  public void onChildMove(FloorItem item, int xy, Direction8 dir)
  {
    removeItem(item, xy, dir);
    setupItem(item);
  }
  
  public void onChildPicked(FloorItem item, int xy, Direction8 dir)
  {
    removeItem(item, xy, dir);
    super.onChildPicked(item, xy, dir);
  }
  
  public boolean addItem(FloorItem item)
  {
    if (super.addItem(item))
    {
      setupItem(item);
      item.addAttachedWired(this);
    }
    return true;
  }
  
  public void setupItem(FloorItem item)
  {
    List<RoomFloorItemData.AffectedTile> PointList = item.getAffectedTiles();
    for (RoomFloorItemData.AffectedTile Tile : PointList)
    {
      List<WiredTriggerBase> triggerList = (List)this.wiredManager.triggersWalksOffFurni.get(Integer.valueOf(Tile.xy));
      if (triggerList == null)
      {
        triggerList = new ArrayList();
        this.wiredManager.triggersWalksOffFurni.put(Integer.valueOf(Tile.xy), triggerList);
      }
      if (triggerList.isEmpty()) {
        item.eventSetFlag(Tile.xy, 512, true);
      }
      triggerList.add(this);
    }
  }
  
  public boolean removeAllItems()
  {
    for (FloorItem item : this.items.values()) {
      removeItem(item, item.getXy(), item.getDir());
    }
    super.removeAllItems();
    return true;
  }
  
  public void cleanDeletedItems()
  {
    for (FloorItem item : this.deletedItems.values()) {
      removeItem(item, item.getXy(), item.getDir());
    }
    super.cleanDeletedItems();
  }
  
  private void removeItem(FloorItem item, int xy, Direction8 dir)
  {
    List<RoomFloorItemData.AffectedTile> PointList = item.getAffectedTiles(xy, dir);
    for (RoomFloorItemData.AffectedTile Tile : PointList)
    {
      List<WiredTriggerBase> triggerList = (List)this.wiredManager.triggersWalksOffFurni.get(Integer.valueOf(Tile.xy));
      if (triggerList != null)
      {
        triggerList.remove(this);
        if (triggerList.isEmpty())
        {
          this.wiredManager.triggersWalksOffFurni.remove(Integer.valueOf(Tile.xy));
          item.eventSetFlag(Tile.xy, 512, false);
        }
      }
    }
  }
}
