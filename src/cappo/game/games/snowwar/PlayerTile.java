package cappo.game.games.snowwar;

public class PlayerTile
{
  private int x;
  private int y;
  private int z;
  
  public PlayerTile(int _arg1, int _arg2, int _arg3)
  {
    this.x = _arg1;
    this.y = _arg2;
    this.z = _arg3;
  }
  
  public int x()
  {
    return this.x;
  }
  
  public int y()
  {
    return this.y;
  }
  
  public int z()
  {
    return this.z;
  }
  
  public void setXYZ(int _arg1, int _arg2, int _arg3)
  {
    this.x = _arg1;
    this.y = _arg2;
    this.z = _arg3;
  }
  
  public void setXY(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  
  public void setXYZ(PlayerTile _arg1)
  {
    this.x = _arg1.x;
    this.y = _arg1.y;
    this.z = _arg1.z;
  }
  
  public int distanceTo(PlayerTile _arg1)
  {
    int local1 = _arg1.x - this.x;
    int local2 = _arg1.y - this.y;
    int local3 = _arg1.z - this.z;
    int local4 = Math.abs(local1) + Math.abs(local2) + Math.abs(local3);
    return local4;
  }
  
  public Direction8 directionTo(PlayerTile _arg1)
  {
    if ((_arg1.x == this.x) && (_arg1.y == this.y)) {
      return null;
    }
    int local1 = _arg1.x - this.x;
    int local2 = _arg1.y - this.y;
    int local3 = Direction360.getRot(local1, local2);
    return Direction360.direction360ValueToDirection8(local3);
  }
  
  public boolean isSamePosition(Object _arg1)
  {
    if (this == _arg1) {
      return true;
    }
    if (!(_arg1 instanceof PlayerTile)) {
      return false;
    }
    PlayerTile local1 = (PlayerTile)_arg1;
    if (this.x != local1.x) {
      return false;
    }
    if (this.y != local1.y) {
      return false;
    }
    if (this.z != local1.z) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return "_x:" + this.x + "yy:" + this.y + "_zz:" + this.z;
  }
  
  public boolean _0Dk(PlayerTile _arg1, int _arg2)
  {
    return _4D8(this.x, this.y, _arg1.x, _arg1.y, _arg2);
  }
  
  public static boolean _4D8(int _arg1, int _arg2, int _arg3, int _arg4, int _arg5)
  {
    int local5 = _arg3 - _arg1;
    if (local5 < 0) {
      local5 = -local5;
    }
    int local6 = _arg4 - _arg2;
    if (local6 < 0) {
      local6 = -local6;
    }
    if ((local6 > _arg5) || (local5 > _arg5)) {
      return false;
    }
    if (local5 * local5 + local6 * local6 < _arg5 * _arg5) {
      return true;
    }
    return false;
  }
}


