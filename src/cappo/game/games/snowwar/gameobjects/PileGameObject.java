package cappo.game.games.snowwar.gameobjects;

import cappo.game.games.snowwar.Direction8;
import cappo.game.games.snowwar.PlayerTile;
import cappo.game.games.snowwar.SnowWarGameStage;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.Tile;

public class PileGameObject
  extends PickBallsGameItemObject
{
  private static int BALLS_SIZE = 100;
  public SnowWarRoom currentSnowWar;
  private int[] boudngingData;
  private final int snowBallsCapacity;
  
  public PileGameObject(int x, int y, int a, int snowBalls, int parentFuseId, SnowWarGameStage _arg2, SnowWarRoom room)
  {
    super(7, _arg2.getTile(x, y), snowBalls, parentFuseId);
    this.snowBallsCapacity = a;
    if (snowBalls > 0) {
      _arg2._2Av(this);
    }
    this.boudngingData = new int[] { snowBalls * BALLS_SIZE };
    this.currentSnowWar = room;
    


    Tile pickUpZones = this.location.getNextTileAtRot(Direction8.N);
    if (pickUpZones != null) {
      pickUpZones.pickBallsItem = this;
    }
    pickUpZones = this.location.getNextTileAtRot(Direction8.S);
    if (pickUpZones != null) {
      pickUpZones.pickBallsItem = this;
    }
    pickUpZones = this.location.getNextTileAtRot(Direction8.E);
    if (pickUpZones != null) {
      pickUpZones.pickBallsItem = this;
    }
    pickUpZones = this.location.getNextTileAtRot(Direction8.W);
    if (pickUpZones != null) {
      pickUpZones.pickBallsItem = this;
    }
  }
  
  public void setSnowBalls(int val)
  {
    this.currentSnowWar.checksum += val * 6 - getVariable(5) * 6;
    this.snowBalls = val;
  }
  
  public int getVariable(int val)
  {
    if (val == 0) {
      return 3;
    }
    if (val == 1) {
      return this.objectId;
    }
    if (val == 2) {
      return this.location.location().x();
    }
    if (val == 3) {
      return this.location.location().y();
    }
    if (val == 4) {
      return this.snowBallsCapacity;
    }
    if (val == 5) {
      return this.snowBalls;
    }
    return this.parentFuseId;
  }
  
  public int[] boundingData()
  {
    return this.boudngingData;
  }
  
  public int _4b8()
  {
    return this.snowBallsCapacity;
  }
  
  public void onSnowballPickup(int ammount)
  {
    setSnowBalls(this.snowBalls - ammount);
    
    this.boudngingData = new int[] { this.snowBalls * BALLS_SIZE };
    if (this.snowBalls <= 0) {
      this.location.removeGameObject();
    }
  }
}


