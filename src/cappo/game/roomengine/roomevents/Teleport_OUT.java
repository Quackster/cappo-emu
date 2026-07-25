package cappo.game.roomengine.roomevents;

import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.live.Avatar;

public class Teleport_OUT
  extends Event
{
  Avatar User;
  GenericFloorItem item;
  
  public Teleport_OUT(Avatar user, GenericFloorItem Item)
  {
    this.User = user;
    this.item = Item;
  }
  
  public void run(RoomTask room)
  {
    byte[] xy = this.item.SquareInFront();
    this.User.moveTo(xy[0], xy[1]);
    room.addUserEvent(new Teleport_CLOSE(this.User, this.item), 1);
    this.User.cn.teleport = null;
  }
}


