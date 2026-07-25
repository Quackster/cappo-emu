package cappo.game.roomengine.itemInteractor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.collections.Utils;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.roomevents.VengingMachineClose;
import java.util.List;

public class InteractorVendingMachine
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item)
  {
    Item.setIntData(0);
  }
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem fitem, int Request, boolean UserHasRights)
  {
    if (User == null) {
      return;
    }
    Avatar avatar = User.avatar;
    
    GenericFloorItem Item = (GenericFloorItem)fitem;
    
    byte[] xy = Item.SquareInFront();
    
    boolean onFront = (avatar.x == xy[0]) && (avatar.y == xy[1]);
    boolean onTop = false;
    if (!onFront) {
      onFront = onTop = (avatar.x == Item.getX()) && (avatar.y == Item.getY());
    }
    if (onFront)
    {
      Item.setIntData(1);
      room.floorItemUpdateNeeded(Item);
      int vendingId = ((Integer)Item.baseItem.vendingIds.get(Utils.GetRandomNumber(0, Item.baseItem.vendingIds.size() - 1))).intValue();
      if (onTop) {
        avatar.SetRot(Item.getDir());
      } else {
        avatar.SetRot(Item.getDir().rotateDirection180Degrees());
      }
      avatar.CarryItem(vendingId);
      room.addItemEvent(new VengingMachineClose(Item), 5);
    }
    else
    {
      avatar.moveTo(xy[0], xy[1]);
    }
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
}


