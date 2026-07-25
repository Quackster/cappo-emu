package cappo.game.roomengine.roomevents;

import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.floor.wired.trigger.WiredTriggerBase;
import cappo.game.roomengine.wired.WiredManager;

public class Item_TIMER
  extends Event
{
  GenericFloorItem Item;
  
  public Item_TIMER(GenericFloorItem item)
  {
    this.Item = item;
  }
  
  public void run(RoomTask room)
  {
    if (room.roomData.haveFlag(64)) {
      if (this.Item.decIntData(1) >= 0)
      {
        room.floorItemUpdateNeeded(this.Item);
        this.Ticks += 2;
      }
      else
      {
        room.roomData.setFlag(64, false);
        WiredTriggerBase.launchTriggers(room.wiredManager.triggersGameEnds, null, null);
      }
    }
  }
}


