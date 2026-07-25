package cappo.game.games.snowwar;

import cappo.engine.logging.Log;

public class Direction8
{
  public static Direction8[] DIRECTIONS = new Direction8[8];
  public static Direction8 N = new Direction8(0, "N", 0, -1);
  public static Direction8 NE = new Direction8(1, "NE", 1, -1);
  public static Direction8 E = new Direction8(2, "E", 1, 0);
  public static Direction8 SE = new Direction8(3, "SE", 1, 1);
  public static Direction8 S = new Direction8(4, "S", 0, 1);
  public static Direction8 SW = new Direction8(5, "SW", -1, 1);
  public static Direction8 W = new Direction8(6, "W", -1, 0);
  public static Direction8 NW = new Direction8(7, "NW", -1, -1);
  private final int rot;
  private final int xDiff;
  private final int yDiff;
  private final String rotName;
  
  public Direction8(int _arg1, String _arg2, int _arg3, int _arg4)
  {
    this.rot = _arg1;
    this.rotName = _arg2;
    this.xDiff = _arg3;
    this.yDiff = _arg4;
    DIRECTIONS[_arg1] = this;
  }
  
  public static Direction8 getDirection(int dir)
  {
    if ((dir < 0) || (dir > 7)) {
      return N;
    }
    return DIRECTIONS[dir];
  }
  
  public static int validateDirection8Value(int dir)
  {
    return dir & 0x7;
  }
  
  public static Direction8 getRot(int curX, int curY, int targetX, int targetY)
  {
    int deltaX = targetX - curX;
    int deltaY = targetY - curY;
    if (deltaX == 0)
    {
      if (deltaY < 0) {
        return N;
      }
      if (deltaY > 0) {
        return S;
      }
    }
    if (deltaX > 0)
    {
      if (deltaY < 0) {
        return NE;
      }
      if (deltaY == 0) {
        return E;
      }
      if (deltaY > 0) {
        return SE;
      }
    }
    if (deltaX < 0)
    {
      if (deltaY < 0) {
        return NW;
      }
      if (deltaY == 0) {
        return W;
      }
      if (deltaY > 0) {
        return SW;
      }
    }
    Log.printLog("ERROR: Direction8.getRot == NULL");
    
    return null;
  }
  
  public int getRot()
  {
    return this.rot;
  }
  
  public Direction8 rotateDirection180Degrees()
  {
    return getDirectionAtRot(4);
  }
  
  public Direction8 rotateDirection45Degrees(boolean _arg1)
  {
    return getDirectionAtRot(_arg1 ? 1 : -1);
  }
  
  public Direction8 rotateDirection90Degrees(boolean _arg1)
  {
    return getDirectionAtRot(_arg1 ? 2 : -2);
  }
  
  public boolean _AC()
  {
    return this.rot % 2 == 0;
  }
  
  public int _3f4()
  {
    return this.rot;
  }
  
  public Direction8 getDirectionAtRot(int diff)
  {
    return DIRECTIONS[validateDirection8Value(this.rot + diff)];
  }
  
  public String toString()
  {
    return this.rotName + "(" + Integer.toString(this.rot) + ")";
  }
  
  public String getRotName()
  {
    return this.rotName;
  }
  
  public int getDiffX()
  {
    return this.xDiff;
  }
  
  public int getDiffY()
  {
    return this.yDiff;
  }
  
  public static boolean haveDirection(Direction8 find, Direction8... directions)
  {
    for (Direction8 val : directions) {
      if (find == val) {
        return true;
      }
    }
    return false;
  }
}


