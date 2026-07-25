package cappo.game.games.snowwar.gameobjects;

import cappo.game.games.snowwar.Direction8;
import cappo.game.games.snowwar.PlayerTile;
import cappo.game.games.snowwar.SnowWarGameStage;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.SynchronizedGameStage;
import cappo.game.games.snowwar.Tile;
import cappo.game.games.snowwar.gameevents.AddBallToMachine;
import java.util.List;

public class MachineGameObject
  extends PickBallsGameItemObject
{
  private static final int SNOWBALLGENERATOR_TIME = 100;
  public static int[] boundingData = { 1200 };
  private final int snowBallsCapacity;
  private final Direction8 direction;
  private int snowBallGeneratorTimer = 100;
  public SnowWarRoom currentSnowWar;
  
  public MachineGameObject(int x, int y, int rot, int a, int b, int c, SnowWarGameStage _arg2, SnowWarRoom room)
  {
    super(8, _arg2.getTile(x, y), b, c);
    this.snowBallsCapacity = a;
    this.direction = Direction8.getDirection(rot);
    _arg2._2Av(this);
    this.currentSnowWar = room;
    
    Tile frontTile = this.location.getNextTileAtRot(Direction8.getDirection((rot + 4) % 8));
    if (frontTile != null) {
      frontTile.pickBallsItem = this;
    }
  }
  
  public void setSnowBalls(int val)
  {
    this.currentSnowWar.checksum += val * 7 - getVariable(6) * 7;
    this.snowBalls = val;
  }
  
  public int getVariable(int val)
  {
    if (val == 0) {
      return 4;
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
      return this.direction.getRot();
    }
    if (val == 5) {
      return this.snowBallsCapacity;
    }
    if (val == 6) {
      return this.snowBalls;
    }
    return this.parentFuseId;
  }
  
  public void subturn(SynchronizedGameStage unused)
  {
    if (this.snowBallGeneratorTimer > 0)
    {
      this.snowBallGeneratorTimer -= 1;
    }
    else
    {
      this.snowBallGeneratorTimer = 100;
      synchronized (this.currentSnowWar.gameEvents)
      {
        this.currentSnowWar.gameEvents.add(new AddBallToMachine(this));
      }
    }
  }
  
  public int[] boundingData()
  {
    return boundingData;
  }
  
  public void addSnowBall()
  {
    if (this.snowBalls < this.snowBallsCapacity) {
      setSnowBalls(this.snowBalls + 1);
    }
  }
  
  public void onSnowballPickup(int ammount)
  {
    setSnowBalls(this.snowBalls - ammount);
  }
/* :0:   */ }


