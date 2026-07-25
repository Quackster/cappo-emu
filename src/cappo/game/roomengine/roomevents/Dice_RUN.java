package cappo.game.roomengine.roomevents;

import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;

public class Dice_RUN
  extends Event
{
  GenericFloorItem Item;
  
  public Dice_RUN(GenericFloorItem item)
  {
    this.Item = item;
  }
  
  public void run(RoomTask room)
  {
    this.Item.setIntData(Utils.GetRandomNumber(1, 6));
    room.floorItemUpdateNeeded(this.Item);
  }
}


