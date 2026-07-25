package cappo.game.player;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.moderation.StaffManager;
import cappo.protocol.messages.composers.moderation.ModeratorInitComposer;

public class PlayerStaff
  extends PlayerData
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
  
  public boolean canKickMe(int rank)
  {
    if (rank > this.staffLevel) {
      return true;
    }
    return false;
  }
  
  public void setupLevelStuff()
  {
    super.setupLevelStuff();
    
    this.connection.giveBadge("HBA");
    if (this.connection.playerData.allowModTools())
    {
      QueueWriter.writeAndFlush(this.connection.socket, ModeratorInitComposer.compose());
      StaffManager.addStaff(this.userId, this.connection);
    }
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


