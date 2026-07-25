package cappo.game.roomengine.roomevents;

import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.live.Avatar;

public class Teleport_CLOSE
  extends Event
{
  GenericFloorItem Item;
  
  public Teleport_CLOSE(Avatar user, GenericFloorItem item)
  {
    this.Item = item;
  }
  
  public void run(RoomTask room)
  {
    this.Item.setIntData(0);
    room.floorItemUpdateNeeded(this.Item);
  }
}


