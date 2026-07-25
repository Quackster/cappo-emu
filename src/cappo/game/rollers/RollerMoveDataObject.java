package cappo.game.rollers;

import cappo.game.roomengine.entity.item.floor.FloorItem;

public class RollerMoveDataObject
  extends RollerMoveData
{
  public FloorItem item;
  
  public RollerMoveDataObject(FloorItem stacked)
  {
    this.item = stacked;
    this.fromZ = stacked.getZ();
  }
}


