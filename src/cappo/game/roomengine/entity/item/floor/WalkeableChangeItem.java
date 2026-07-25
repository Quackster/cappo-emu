package cappo.game.roomengine.entity.item.floor;

import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.SquareFlagManager;
import java.util.List;

public class WalkeableChangeItem
  extends GenericFloorItem
{
  private void updateHole(boolean walkeable)
  {
    RoomTask room = getRoom();
    List<RoomFloorItemData.AffectedTile> PointList = getAffectedTiles();
    for (RoomFloorItemData.AffectedTile Tile : PointList) {
      room.squareFlag.SetFlag(Tile.xy, 4, walkeable);
    }
  }
  
  public void setIntData(int data)
  {
    super.setIntData(data);
    updateHole(data != 0);
  }
  
  public int incIntData(int ammount)
  {
    int data = super.incIntData(ammount);
    updateHole(data != 0);
    return data;
  }
  
  public int incIntDataMod(int ammount, int modulus)
  {
    int data = super.incIntDataMod(ammount, modulus);
    updateHole(data != 0);
    return data;
  }
  
  public int decIntData(int ammount)
  {
    int data = super.decIntData(ammount);
    updateHole(data != 0);
    return data;
  }
}


