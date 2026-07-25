package cappo.game.roomengine.entity.item.floor.wired;

import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AtachedWireds
{
  private final Map<Integer, WiredItemBase> wireds;
  
  public AtachedWireds(WiredItemBase wired)
  {
    this.wireds = new ConcurrentHashMap(3);
    addWired(wired);
  }
  
  public void addWired(WiredItemBase wired)
  {
    this.wireds.put(Integer.valueOf(wired.itemId), wired);
  }
  
  public void removeWired(int itemId)
  {
    this.wireds.remove(Integer.valueOf(itemId));
  }
  
  public void itemMoved(FloorItem item, int xy, Direction8 dir)
  {
    for (WiredItemBase wired : this.wireds.values()) {
      wired.onChildMove(item, xy, dir);
    }
  }
  
  public void itemPicked(FloorItem item, int xy, Direction8 dir)
  {
    for (WiredItemBase wired : this.wireds.values()) {
      wired.onChildPicked(item, xy, dir);
    }
  }
}


