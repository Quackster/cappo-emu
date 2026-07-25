package cappo.game.player;

import cappo.engine.player.Connection;

public class PlayerSecurity
  extends PlayerModerator
{
  public static boolean allowRoomAlert;
  public static boolean allowPickFurni;
  public static boolean allowEjectFurni;
  public static boolean allowRoomControl;
  public static boolean allowModTools;
  public static boolean allowBan;
  public static boolean allowGiveBadge;
  public static boolean allowHotelAlert;
  public static boolean allowGiveMoney;
  
  public void setupLevelStuff()
  {
    super.setupLevelStuff();
    
    this.connection.giveBadge("ADM");
  }
  
  public boolean allowRoomAlert()
  {
    return allowRoomAlert;
  }
  
  public boolean allowPickFurni()
  {
    return allowPickFurni;
  }
  
  public boolean allowEjectFurni()
  {
    return allowEjectFurni;
  }
  
  public boolean allowRoomControl()
  {
    return allowRoomControl;
  }
  
  public boolean allowModTools()
  {
    return allowModTools;
  }
  
  public boolean allowBan()
  {
    return allowBan;
  }
  
  public boolean allowGiveBadge()
  {
    return allowGiveBadge;
  }
  
  public boolean allowHotelAlert()
  {
    return allowHotelAlert;
  }
  
  public boolean allowGiveMoney()
  {
    return allowGiveMoney;
  }
}


