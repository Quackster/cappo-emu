package cappo.game.games.snowwar.gameobjects;

import cappo.game.games.snowwar.Direction360;
import cappo.game.games.snowwar.PlayerTile;
import cappo.game.games.snowwar.Tile;

public abstract class PickBallsGameItemObject
  extends GameItemObject
{
  protected int parentFuseId;
  protected int snowBalls;
  protected Tile location;
  public int concurrentUses;
  
  public PickBallsGameItemObject(int _arg1, Tile _arg2, int _arg3, int _arg4)
  {
    super(_arg1);
    this.location = _arg2;
    this.snowBalls = _arg3;
    this.parentFuseId = _arg4;
  }
  
  public Direction360 direction360()
  {
    return null;
  }
  
  public PlayerTile location3D()
  {
    return this.location.location();
  }
  
  public int _4rk()
  {
    return this.parentFuseId;
  }
  
  public boolean canPickUpFromHere()
  {
    return this.snowBalls > this.concurrentUses;
  }
  
  public int pickUp(int ammount)
  {
    if (this.snowBalls < ammount) {
      ammount = this.snowBalls;
    }
    onSnowballPickup(ammount);
    return ammount;
  }
  
  public abstract void onSnowballPickup(int paramInt);
}


