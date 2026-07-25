package cappo.game.games.snowwar.gameobjects;

import cappo.game.games.snowwar.Direction360;
import cappo.game.games.snowwar.Direction8;
import cappo.game.games.snowwar.PlayerTile;
import cappo.game.games.snowwar.SnowWarGameStage;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.Tile;

public class TreeGameObject
  extends GameItemObject
{
  private static int[] _jU = new int[1];
  private static int[] _2Kl = { Tile.TILE_SIZE - SnowBallGameObject.boundingData[0] - 1 };
  private final int parentFuseId;
  private final Tile _0QF;
  private final Direction8 _direction8;
  private final Direction360 _direction360;
  private final int _height;
  private final int _ws;
  private int currentDamage;
  public SnowWarRoom currentSnowWar;
  
  public TreeGameObject(int x, int y, int rot, int height, int a, int b, int c, SnowWarGameStage _arg2, SnowWarRoom room)
  {
    super(9);
    
    this.currentSnowWar = room;
    
    this._0QF = _arg2.getTile(x, y);
    this._direction8 = Direction8.getDirection(rot);
    this._direction360 = new Direction360(Direction360.direction8ToDirection360Value(this._direction8));
    this.parentFuseId = a;
    this._height = (height * Tile.TILE_SIZE);
    this.currentDamage = c;
    this._ws = b;
    
    _arg2._2Av(this);
    
    this._0QF._4AO(-this._height);
    this._0QF.setBlocked(true);
  }
  
  public void setDamage(int val)
  {
    this.currentSnowWar.checksum += val * 9 - getVariable(8) * 9;
    this.currentDamage = val;
  }
  
  public int getVariable(int val)
  {
    if (val == 0) {
      return 2;
    }
    if (val == 1) {
      return this.objectId;
    }
    if (val == 2) {
      return this._0QF.location().x();
    }
    if (val == 3) {
      return this._0QF.location().y();
    }
    if (val == 4) {
      return this._direction8.getRot();
    }
    if (val == 5) {
      return this._height;
    }
    if (val == 6) {
      return this.parentFuseId;
    }
    if (val == 7) {
      return this._ws;
    }
    return this.currentDamage;
  }
  
  public int[] boundingData()
  {
    if (this.currentDamage < this._ws) {
      return _2Kl;
    }
    return _jU;
  }
  
  public PlayerTile location3D()
  {
    return this._0QF.location();
  }
  
  public Direction360 direction360()
  {
    return this._direction360;
  }
  
  public void onSnowBallHit(SnowBallGameObject _arg2)
  {
    if (this.currentDamage < this._ws) {
      setDamage(this.currentDamage + 1);
    } else {
      this._0QF.removeGameObject();
    }
  }
  
  public int _4ZU()
  {
    return this._ws;
  }
  
  public int _2Ti()
  {
    return this.currentDamage;
  }
  
  public int _4rk()
  {
    return this.parentFuseId;
  }
  
  public int collisionHeight()
  {
    return this._height;
  }
}


