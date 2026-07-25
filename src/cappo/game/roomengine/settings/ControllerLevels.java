package cappo.game.roomengine.settings;

import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import java.util.Map;

public class ControllerLevels
{
  public static final int LEVEL_NONE = 0;
  public static final int LEVEL_RIGHTS = 1;
  public static final int LEVEL_GROUP_MEMBER = 2;
  public static final int LEVEL_GROUP_ADMIN = 3;
  public static final int LEVEL_ROOM_OWNER = 4;
  public static final int LEVEL_STAFF = 5;
  
  public static int getLevel(PlayerData User, RoomData roomData, RoomTask room)
  {
    if (User.allowRoomControl()) {
      return 5;
    }
    if (roomData.roomOwnerId > 0)
    {
      if (User.userId == roomData.roomOwnerId) {
        return 4;
      }
    }
    else if (User.userName.equals(roomData.roomOwnerName)) {
      return 4;
    }
    if ((room != null) && 
      (room.usersWithRights.containsKey(Integer.valueOf(User.userId)))) {
      return 1;
    }
    return 0;
  }
}


