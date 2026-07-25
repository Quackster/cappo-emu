package cappo.protocol.messages.events.room.action;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.settings.ModerationPermissions;
import cappo.protocol.messages.IncomingMessageEvent;

public class BanUserParser
  extends IncomingMessageEvent
{
  private static final String BAN_USER_HOUR = "RWUAM_BAN_USER_HOUR";
  private static final String BAN_USER_DAY = "RWUAM_BAN_USER_DAY";
  private static final String BAN_USER_PERM = "RWUAM_BAN_USER_PERM";
  
  public void messageReceived(Connection cn)
  {
    Avatar avatar = cn.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    RoomData roomData = room.roomData;
    if (roomData.modPermissions.permissionsBan == 1)
    {
      if ((avatar.controllerLevel == 1) || 
        (avatar.controllerLevel >= 3)) {}
    }
    else if (avatar.controllerLevel < 4) {
      return;
    }
    PlayerData client = Clients.getPlayerData(cn.currentPacket.readInt());
    if ((client == null) || (client.connection.avatar == null)) {
      return;
    }
    if ((client.staffLevel > 1) && (client.staffLevel >= cn.getPlayerData().staffLevel)) {
      return;
    }
    cn.currentPacket.readInt();
    
    String type = cn.currentPacket.readString();
    if (type.equals("RWUAM_BAN_USER_HOUR")) {
      room.addBan(client, 3600);
    } else if (type.equals("RWUAM_BAN_USER_DAY")) {
      room.addBan(client, 86400);
    } else if (type.equals("RWUAM_BAN_USER_PERM")) {
      room.addBan(client, 30000000);
    }
    if (client.connection.avatar.room == room) {
      room.removeUserFromRoom(client.connection, true, true);
    }
  }
}


