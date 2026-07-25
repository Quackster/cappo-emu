package cappo.game.roomengine.itemInteractor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.roomevents.Dice_RUN;

public class InteractorDice
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item)
  {
    Item.setIntData(0);
  }
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem fitem, int request, boolean UserHasRights)
  {
    GenericFloorItem item = (GenericFloorItem)fitem;
    if (item.baseItem.interactorType != Interactor.InteractorType.dice) {
      return;
    }
    if (item.getIntData() == -1) {
      return;
    }
    if (User != null)
    {
      int difX = item.getX() - User.avatar.x;
      int difY = item.getY() - User.avatar.y;
      if ((difX > 1) || (difX < -1) || (difY > 1) || (difY < -1)) {
        return;
      }
    }
    if (request != -1)
    {
      item.setIntData(-1);
      room.floorItemUpdateNeeded(item);
      room.addItemEvent(new Dice_RUN(item), 5);
    }
    else if (item.getIntData() > 0)
    {
      item.setIntData(0);
      room.floorItemUpdateNeeded(item);
    }
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
}


