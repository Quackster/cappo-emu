package cappo.game.roomengine.itemInteractor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;

public class InteractorSimple
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item) {}
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem fItem, int Request, boolean UserHasRights)
  {
    if (!UserHasRights) {
      return;
    }
    if (fItem.baseItem.interactionCount < 2) {
      return;
    }
    GenericFloorItem Item = (GenericFloorItem)fItem;
    
    Item.incIntDataMod(1, Item.baseItem.interactionCount);
    
    room.floorItemUpdateNeeded(Item);
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights)
  {
    if (!UserHasRights) {
      return;
    }
    if (Item.baseItem.interactionCount < 2) {
      return;
    }
    Item.setIntData(Item.incIntData(1) % Item.baseItem.interactionCount);
    room.wallItemUpdateNeeded(Item);
  }
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
}


