package cappo.game.roomengine;

import java.util.HashSet;
import java.util.Set;

public final class Square
{
  public static final int HEIGHT_MULT = 256;
  public static final int FLAG_BLOCKED = 16384;
  public static final int MAX_HEIGHT = 64;
  public Set<Square> adjacencies = new HashSet(4, 1.0F);
  public Set<Square> adjacenciesNoDiagonal = new HashSet(4, 1.0F);
  public float height;
  public int x;
  public int y;
  public int xy;
  
  public Square(int x, int y, int xy, float z)
  {
    this.x = x;
    this.y = y;
    this.xy = xy;
    this.height = z;
  }
  
  public final int getLocalCost(Square start, Square goal)
  {
    if (this.xy == start.xy) {
      return 999999;
    }
    return Math.abs(this.x - goal.x) + Math.abs(this.y - goal.y);
  }
  
  public boolean equals(Object arg0)
  {
    return ((Square)arg0).xy == this.xy;
  }
  
  public int hashCode()
  {
    return this.xy;
  }
}


