package cappo.game.roomengine;
import cappo.game.roomengine.roomlisting.RoomListing;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.threadpools.RoomTask;
import cappo.game.navigator.NavigatorCategories;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.roomlisting.RoomListing.ListingRoomState;
import cappo.game.roomengine.settings.ChatSettings;
import cappo.game.roomengine.settings.ModerationPermissions;
import cappo.game.roomengine.settings.TradingSettings;

public final class RoomData
{
  public String model;
  public long lastUsedThis;
  public String[] tags;
  public boolean muteAllOn;
  public int roomId;
  public int BotCounter;
  public int category;
  public int rating;
  public int state;
  public int usersMax;
  public int roomOwnerId;
  public int ranking;
  public int floorAnchor;
  public int wallAnchor;
  public String roomOwnerName;
  public String description;
  public String Floor;
  public String Wallpaper;
  public String Landscape;
  public String name;
  public String password;
  public RoomTask room;
  public PlayerData roomOwner;
  public RoomEvent event;
  public RoomIcon icon;
  public ModerationPermissions modPermissions;
  public TradingSettings tradingSettings;
  public ChatSettings chatSettings;
  private int flags;
  public final RoomListing.ListingRoomState[] roomListingState;
  
  public RoomData(int id, int maxUsers)
  {
    this.roomId = id;
    this.usersMax = maxUsers;
    this.roomListingState = new RoomListing.ListingRoomState[2 + (1 + NavigatorCategories.MAX_ID)];
  }
  
  public boolean haveFlag(int bit)
  {
    return (this.flags & bit) != 0;
  }
  
  public final void setFlag(int flag, boolean add)
  {
    if (add) {
      this.flags |= flag;
    } else {
      this.flags &= (flag ^ 0xFFFFFFFF);
    }
  }
  
  public final void xorFlag(int flag)
  {
    this.flags ^= flag;
  }
  
  public void updateMaxUsers(int Max)
  {
    this.usersMax = Max;
    if (this.room != null) {
      this.room.updateMaxUsers(Max);
    }
  }
  
  public void delete()
  {
    try
    {
      Database.exec("DELETE FROM rooms WHERE `id`='" + this.roomId + "';", new Object[0]);
    }
    catch (Exception ex)
    {
      Log.printException("saveItems", ex);
    }
    removeRoomPets();
    removeRoomBots();
    deleteRoomDiscs();
  }
  
  private void removeRoomPets()
  {
    try
    {
      Database.exec("UPDATE user_pets SET `room_id`='0' WHERE `room_id`='" + this.roomId + "';", new Object[0]);
    }
    catch (Exception ex)
    {
      Log.printException("saveItems", ex);
    }
  }
  
  private void removeRoomBots()
  {
    try
    {
      Database.exec("UPDATE user_bots SET `room_id`='0' WHERE `room_id`='" + this.roomId + "';", new Object[0]);
    }
    catch (Exception ex)
    {
      Log.printException("saveItems", ex);
    }
  }
  
  private void deleteRoomDiscs()
  {
    try
    {
      Database.exec("DELETE FROM room_discs WHERE `roomid`='" + this.roomId + "';", new Object[0]);
    }
    catch (Exception ex)
    {
      Log.printException("saveItems", ex);
    }
  }
}
