package cappo.game.roomengine.entity.item.floor.wired.condition;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData;

import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData.AffectedTile;
import cappo.game.roomengine.entity.live.Avatar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FurniHasSelectedUser
  extends WiredConditionBase
{
  public int getCode()
  {
    return 8;
  }
  
  public boolean needUser()
  {
    return true;
  }
  
  public boolean checkCondition(Connection invoker)
  {
    for (FloorItem floorItem : this.items.values())
    {
      List<RoomFloorItemData.AffectedTile> PointList = floorItem.getAffectedTiles();
      for (RoomFloorItemData.AffectedTile Tile : PointList)
      {
        if (invoker.avatar.xy == Tile.xy) {
          return true;
        }
      }
    }
    return false;
  }
}
