package cappo.game.roomengine.settings;

public class TradingSettings
{
  public static final int NONE = 0;
  public static final int WITH_RIGHTS = 1;
  public static final int ALL = 2;
  public int permissions;
  
  public TradingSettings(int data)
  {
    this.permissions = data;
  }
  
  public int getIntValue()
  {
    return this.permissions;
  }
}


