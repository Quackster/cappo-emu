package cappo.game.roomengine.roomevents;

import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;

public class VengingMachineClose
  extends Event
{
  GenericFloorItem Item;
  
  public VengingMachineClose(GenericFloorItem item)
  {
    this.Item = item;
  }
  
  public void run(RoomTask room)
  {
    this.Item.setIntData(0);
    room.floorItemUpdateNeeded(this.Item);
  }
}


