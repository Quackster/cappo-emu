package cappo.game.roomengine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SquareFlagManager
{
  private final Map<Short, Integer> events;
  private final Map<Short, Integer> flags;
  public static final int squareLAY = 1;
  public static final int squareSIT = 2;
  public static final int squareWALKABLE = 4;
  public static final int squareWALKABLE_LASTSTEP = 8;
  public static final int squareEVENT = 16;
  public static final int squareEvent_GameGate = 1;
  public static final int squareEvent_Roller = 2;
  public static final int squareEvent_Skates = 4;
  public static final int squareEvent_BanzaiTile = 8;
  public static final int squareEvent_BanzaiPuck = 16;
  public static final int squareEvent_FootballBall = 32;
  public static final int squareEvent_FootballGoal = 64;
  public static final int squareEvent_Water = 128;
  public static final int squareEvent_WiredWalkIn = 256;
  public static final int squareEvent_WiredWalkOut = 512;
  
  public SquareFlagManager()
  {
    this.events = new ConcurrentHashMap();
    this.flags = new ConcurrentHashMap();
  }
  
  public boolean eventHave(int a, int flag)
  {
    short xy = (short)a;
    
    Integer bits = (Integer)this.events.get(Short.valueOf(xy));
    if (bits == null) {
      return false;
    }
    return (bits.intValue() & flag) > 0;
  }
  
  public final void eventSetFlag(int a, int flag, boolean Add)
  {
    short xy = (short)a;
    
    Integer bits = (Integer)this.events.get(Short.valueOf(xy));
    if (bits == null)
    {
      if (!Add) {
        return;
      }
      this.events.put(Short.valueOf(xy), Integer.valueOf(flag));
    }
    else if (Add)
    {
      this.events.put(Short.valueOf(xy), Integer.valueOf(bits.intValue() | flag));
    }
    else
    {
      this.events.put(Short.valueOf(xy), Integer.valueOf(bits.intValue() & (flag ^ 0xFFFFFFFF)));
    }
  }
  
  public boolean have(int a, int flag)
  {
    short xy = (short)a;
    
    Integer bits = (Integer)this.flags.get(Short.valueOf(xy));
    if (bits == null) {
      return false;
    }
    return (bits.intValue() & flag) > 0;
  }
  
  public final void SetFlag(int a, int flag, boolean Add)
  {
    short xy = (short)a;
    
    Integer bits = (Integer)this.flags.get(Short.valueOf(xy));
    if (bits == null)
    {
      if (!Add) {
        return;
      }
      this.flags.put(Short.valueOf(xy), Integer.valueOf(flag));
    }
    else if (Add)
    {
      this.flags.put(Short.valueOf(xy), Integer.valueOf(bits.intValue() | flag));
    }
    else
    {
      this.flags.put(Short.valueOf(xy), Integer.valueOf(bits.intValue() & (flag ^ 0xFFFFFFFF)));
    }
  }
}


