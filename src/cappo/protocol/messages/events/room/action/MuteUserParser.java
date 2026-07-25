package cappo.protocol.messages.events.room.action;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.chat.UserRoomMuted;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.settings.ModerationPermissions;
import cappo.protocol.messages.IncomingMessageEvent;

public class MuteUserParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    Avatar avatar = cn.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    RoomData roomData = room.roomData;
    if (roomData.modPermissions.permissionsMute == 1)
    {
      if ((avatar.controllerLevel == 1) || 
        (avatar.controllerLevel >= 3)) {}
    }
    else if (avatar.controllerLevel < 4) {
      return;
    }
    PlayerData client = Clients.getPlayerData(cn.currentPacket.readInt());
    if ((client == null) || (client.connection == null)) {
      return;
    }
    if ((client.staffLevel > 1) && (client.staffLevel >= cn.playerData.staffLevel)) {
      return;
    }
    cn.currentPacket.readInt();
    
    Avatar clientAvatar = client.connection.avatar;
    if (clientAvatar.room == room)
    {
      clientAvatar.userRoomMuted = new UserRoomMuted();
      clientAvatar.userRoomMuted.unMuteTimeStamp = (Utils.getTimestamp() + cn.currentPacket.readInt() * 60);
    }
  }
}


