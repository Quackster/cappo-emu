package cappo.game.rollers;

import cappo.game.roomengine.entity.live.LiveEntity;

public class RollerMoveDataEntity
  extends RollerMoveData
{
  public LiveEntity entity;
  public int entityMoveType;
  
  public RollerMoveDataEntity(LiveEntity stacked, int movetype)
  {
    this.entity = stacked;
    this.fromZ = stacked.z;
    this.entityMoveType = movetype;
  }
}


