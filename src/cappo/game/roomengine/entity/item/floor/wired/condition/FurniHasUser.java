package cappo.game.roomengine.entity.item.floor.wired.condition;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData.AffectedTile;
import java.util.List;
import java.util.Map;

public class FurniHasUser
  extends WiredConditionBase
{
  public int getCode()
  {
    return 2;
  }
  
  public boolean needUser()
  {
    return false;
  }
  
  public boolean checkCondition(Connection invoker)
  {
    for (FloorItem floorItem : this.items.values())
    {
      boolean haveUser = false;
      
      List<RoomFloorItemData.AffectedTile> PointList = floorItem.getAffectedTiles();
      for (RoomFloorItemData.AffectedTile Tile : PointList) {
        if (getRoom().squareHasUsers(Tile.xy))
        {
          haveUser = true;
          break;
        }
      }
      if (!haveUser) {
        return false;
      }
    }
    return true;
  }
}
