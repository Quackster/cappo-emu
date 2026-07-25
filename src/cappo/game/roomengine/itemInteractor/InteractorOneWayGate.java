package cappo.game.roomengine.itemInteractor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;

public class InteractorOneWayGate
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item)
  {
    Item.setIntData(0);
  }
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem floorItem, int Request, boolean UserHasRights)
  {
    if (User == null) {
      return;
    }
    Avatar avatar = User.avatar;
    
    GenericFloorItem item = (GenericFloorItem)floorItem;
    
    byte[] xy = floorItem.SquareInFront();
    if (((avatar.x == xy[0]) && (avatar.y == xy[1])) || ((avatar.x == floorItem.getX()) && (avatar.y == floorItem.getY())))
    {
      item.setIntData(1);
      room.floorItemUpdateNeeded(floorItem);
      xy = floorItem.SquareBehind();
      avatar.allowOverride = true;
      avatar.moveTo(xy[0], xy[1]);
    }
    else
    {
      avatar.moveTo(xy[0], xy[1]);
    }
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
}


