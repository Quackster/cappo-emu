package cappo.game.games.snowwar.gameobjects;

import cappo.game.games.snowwar.Direction360;
import cappo.game.games.snowwar.Direction8;
import cappo.game.games.snowwar.MathUtil;
import cappo.game.games.snowwar.PlayerTile;
import cappo.game.games.snowwar.SnowWarGameStage;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.SquareRoot;
import cappo.game.games.snowwar.SynchronizedGameStage;
import cappo.game.games.snowwar.Tile;

public class SnowBallGameObject
  extends GameItemObject
{
  public static int _4s = 0;
  public static int _09I = 1;
  public static int _x2 = 2;
  public static int TOPLAYER = 3;
  public static int _2pR = 2000;
  public static int _2t9 = 3000;
  public static double _1Zn = 0.000707213578500707D;
  public static double _tO = 0.000559D;
  public static int _0v2 = 20000;
  public static int _3uf = 60000;
  public static int _3Xd = 100000;
  public static int _3uE = 42000;
  public static int _2Sx = 10;
  public static int _0zg = 25;
  public static int _3kL = 50;
  public static int _0Wg = 3;
  public static int _0Po = 15;
  public static int[] boundingData = { 400 };
  public SnowWarRoom currentSnowWar;
  public int originX;
  public int originY;
  private final PlayerTile currentLocation;
  private final Direction360 direction;
  private int launchType;
  private int speed;
  private int ttl;
  private HumanGameObject ballOwner;
  private int paraOffs;
  
  public SnowBallGameObject(SnowWarRoom room)
  {
    super(11);
    this.currentSnowWar = room;
    this.currentLocation = new PlayerTile(0, 0, 0);
    this.direction = new Direction360(0);
  }
  
  public void setCurLocation(int x, int y, int z)
  {
    this.currentSnowWar.checksum += x * 3 - getVariable(2) * 3;
    this.currentSnowWar.checksum += y * 4 - getVariable(3) * 4;
    this.currentSnowWar.checksum += z * 5 - getVariable(4) * 5;
    this.currentLocation.setXYZ(x, y, z);
  }
  
  public void setTtl(int val)
  {
    this.currentSnowWar.checksum += val * 8 - getVariable(7) * 8;
    this.ttl = val;
  }
  
  public void setOwner(HumanGameObject val)
  {
    if (val == null) {
      this.currentSnowWar.checksum += -9 - getVariable(8) * 9;
    } else {
      this.currentSnowWar.checksum += val.objectId * 9 - getVariable(8) * 9;
    }
    this.ballOwner = val;
  }
  
  public void initialize(int x, int y, int type, int destX, int destY, HumanGameObject owner)
  {
    this.originX = x;
    this.originY = y;
    this.currentLocation.setXYZ(x, y, _2t9);
    
    int deltaX = destX - x;
    int deltaY = destY - y;
    deltaX = MathUtil._43Z(deltaX / 200);
    deltaY = MathUtil._43Z(deltaY / 200);
    this.direction._1ji(Direction360.getRot(deltaX, deltaY));
    
    int local7 = SquareRoot.sqrt(deltaX * deltaX + deltaY * deltaY) * 200;
    this.launchType = type;
    getMoveType(type, local7);
    if (this.launchType == _4s)
    {
      this.ttl = (_0v2 / _2pR);
      this.speed = _2pR;
    }
    else if (this.launchType == _09I)
    {
      local7 = Math.min(local7, _3uf);
      this.ttl = ((int)(local7 * _tO));
      this.speed = (this.ttl == 0 ? 0 : MathUtil._43Z(local7 / this.ttl));
    }
    else if (this.launchType == _x2)
    {
      local7 = Math.min(local7, _3Xd);
      this.ttl = ((int)(local7 * _1Zn));
      this.speed = (this.ttl == 0 ? 0 : MathUtil._43Z(local7 / this.ttl));
    }
    this.paraOffs = MathUtil._43Z(this.ttl / 2);
    this.ballOwner = owner;
  }
  
  private void getMoveType(int _arg1, int _arg2)
  {
    if (_arg1 == TOPLAYER)
    {
      if (_arg2 <= _3uE) {
        this.launchType = _4s;
      } else if (_arg2 <= _3uf) {
        this.launchType = _09I;
      } else {
        this.launchType = _x2;
      }
    }
    else {
      this.launchType = _arg1;
    }
  }
  
  public int getVariable(int val)
  {
    if (val == 0) {
      return 1;
    }
    if (val == 1) {
      return this.objectId;
    }
    if (val == 2) {
      return this.currentLocation.x();
    }
    if (val == 3) {
      return this.currentLocation.y();
    }
    if (val == 4) {
      return this.currentLocation.z();
    }
    if (val == 5) {
      return this.direction._2Hq();
    }
    if (val == 6) {
      return this.launchType;
    }
    if (val == 7) {
      return this.ttl;
    }
    if (val == 8) {
      return this.ballOwner == null ? -1 : this.ballOwner.objectId;
    }
    if (val == 9) {
      return this.paraOffs;
    }
    return this.speed;
  }
  
  public Direction360 direction360()
  {
    return this.direction;
  }
  
  public int[] boundingData()
  {
    return boundingData;
  }
  
  public PlayerTile location3D()
  {
    return this.currentLocation;
  }
  
  public void subturn(SynchronizedGameStage _arg1)
  {
    SnowWarRoom local1 = (SnowWarRoom)_arg1;
    if (!this._active) {
      return;
    }
    setTtl(this.ttl - 1);
    if (this.launchType == _4s) {
      _3rG(_2Sx, true);
    } else if (this.launchType == _09I) {
      _3rG(_0zg, false);
    } else {
      _3rG(_3kL, false);
    }
    int local2 = Tile._4mC(this.currentLocation.x());
    int local3 = Tile._3FS(this.currentLocation.y());
    Tile local4 = local1.map.getTile(local2, local3);
    boolean collision = checkCollision(local4);
    if (!collision) {
      collision = local1.map.checkFloorCollision(this);
    }
    if (collision) {
      local1.queueDeleteObject(this);
    }
  }
  
  private boolean checkCollision(Tile _arg2)
  {
    boolean local2 = false;
    if (_arg2 != null)
    {
      local2 = testSnowBallCollision(_arg2);
      if (!local2)
      {
        Direction8 local3 = this.direction.direction8Value();
        local2 = testSnowBallCollision(_arg2.getNextTileAtRot(local3));
        if (!local2)
        {
          local2 = testSnowBallCollision(_arg2.getNextTileAtRot(local3.rotateDirection45Degrees(false)));
          if (!local2) {
            local2 = testSnowBallCollision(_arg2.getNextTileAtRot(local3.rotateDirection45Degrees(true)));
          }
        }
      }
    }
    return local2;
  }
  
  private boolean testSnowBallCollision(Tile _arg2)
  {
    if (_arg2 != null)
    {
      GameItemObject item = _arg2._4fe();
      if (item != null) {
        if (item.testSnowBallCollision(this))
        {
          item.onSnowBallHit(this);
          return true;
        }
      }
    }
    return false;
  }
  
  private void _3rG(int _arg1, boolean _arg2)
  {
    int local2 = this.currentLocation.x() + MathUtil._43Z(this.direction._0uc() * this.speed / 255);
    int local3 = this.currentLocation.y() + MathUtil._43Z(this.direction._17x() * this.speed / 255);
    int local4 = this.ttl - this.paraOffs;
    int local5 = (this.paraOffs * this.paraOffs - local4 * local4) * _arg1 + _2t9;
    if (_arg2) {
      local5 = Math.min(local5, _2t9);
    }
    setCurLocation(local2, local3, local5);
  }
  
  public String toString()
  {
    return " location=(" + this.currentLocation.x() + "," + this.currentLocation.y() + "," + this.currentLocation.z() + ")" + " dir=" + this.direction + " paraOffs=" + this.paraOffs + " ttl=" + this.ttl;
  }
  
  public HumanGameObject getAttacker()
  {
    return this.ballOwner;
  }
  
  public void onRemove() {}
}


