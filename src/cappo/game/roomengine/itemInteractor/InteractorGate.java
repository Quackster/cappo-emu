package cappo.game.roomengine.itemInteractor;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData.AffectedTile;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import java.util.List;

public class InteractorGate
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item) {}
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem fitem, int Request, boolean UserHasRights)
  {
    if (!UserHasRights) {
      return;
    }
    GenericFloorItem floorItem = (GenericFloorItem)fitem;
    if (floorItem.baseItem.interactionCount < 2) {
      return;
    }
    floorItem.setIntData((floorItem.getIntData() + 1) % floorItem.baseItem.interactionCount);
    
    boolean walkeable = floorItem.getIntData() != 0;
    List<RoomFloorItemData.AffectedTile> PointList = floorItem.getAffectedTiles();
    for (RoomFloorItemData.AffectedTile Tile : PointList) {
      room.squareFlag.SetFlag(Tile.xy, 4, walkeable);
    }
    room.floorItemUpdateNeeded(floorItem);
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
}
