package cappo.game.player;

import cappo.engine.player.Connection;
import cappo.game.player.messenger.PlayerMessenger;

public abstract class PlayerData
{
  public static final int SECURITY_NONE = 0;
  public static final int SECURITY_PANCHO = 1;
  public static final int SECURITY_LINCE = 2;
  public static final int SECURITY_BOT = 3;
  public static final int SECURITY_STAFF = 4;
  public static final int SECURITY_MOD = 5;
  public static final int SECURITY_SECURITYMANAGER = 6;
  public static final int SECURITY_COMUNITYMANAGER = 7;
  public static final int SECURITY_MANAGER = 8;
  public static final int SECURITY_DEVELOPER = 9;
  public static Class<?>[] securityLevelPlr = new Class[10];
  public int userId;
  public int staffLevel;
  public int sex;
  public String email;
  public String userName;
  public String motto;
  public AvatarLook avatarLook;
  public Connection connection;
  
  static
  {
    securityLevelPlr[0] = PlayerNormal.class;
    securityLevelPlr[1] = PanchoPantera.class;
    securityLevelPlr[2] = PlayerLince.class;
    securityLevelPlr[3] = PlayerBot.class;
    securityLevelPlr[4] = PlayerStaff.class;
    securityLevelPlr[5] = PlayerModerator.class;
    securityLevelPlr[6] = PlayerSecurity.class;
    securityLevelPlr[7] = PlayerCommunity.class;
    securityLevelPlr[8] = PlayerManager.class;
    securityLevelPlr[9] = PlayerDeveloper.class;
  }
  
  public static PlayerData getPlayer(int level)
    throws Exception
  {
    PlayerData player = (PlayerData)securityLevelPlr[level].newInstance();
    player.staffLevel = level;
    return player;
  }
  
  public void setupLevelStuff()
  {
    this.connection.giveBadge("Z63");
    this.connection.giveBadge("Z64");
  }
  
  public abstract boolean allowRoomAlert();
  
  public abstract boolean allowPickFurni();
  
  public abstract boolean allowEjectFurni();
  
  public abstract boolean allowRoomControl();
  
  public abstract boolean allowModTools();
  
  public abstract boolean allowBan();
  
  public abstract boolean allowGiveBadge();
  
  public abstract boolean allowHotelAlert();
  
  public abstract boolean allowGiveMoney();
  
  public boolean allowSuperAds()
  {
    return false;
  }
  
  public boolean allowHotelImageAlert()
  {
    return false;
  }
  
  public boolean allowDataReload()
  {
    return false;
  }
  
  public boolean canKickMe(int rank)
  {
    return true;
  }
  
  public boolean useChatBot()
  {
    return false;
  }
  
  public String getRealName()
  {
    return "";
  }
  
  public PlayerMessenger messenger = new PlayerMessenger(this);
  public long LastUsedThis;
  public int AchievementsScore;
  public long registerDate;
  public long lastVisit;
  public int bans;
  public int cautions;
  public int cfhs;
  public int cfhs_abusive;
  
  public boolean equals(Object arg0)
  {
    return ((PlayerData)arg0).userId == this.userId;
  }
  
  public int hashCode()
  {
    return this.userId;
  }
}


