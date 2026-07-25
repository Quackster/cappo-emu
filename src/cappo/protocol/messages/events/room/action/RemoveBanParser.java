package cappo.protocol.messages.events.room.action;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.settings.ModerationPermissions;
import cappo.game.roomengine.settings.PlayerBan;
import cappo.protocol.messages.IncomingMessageEvent;

public class RemoveBanParser
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
    if (roomData.modPermissions.permissionsBan == 1)
    {
      if ((avatar.controllerLevel == 1) || 
        (avatar.controllerLevel >= 3)) {}
    }
    else if (avatar.controllerLevel < 4) {
      return;
    }
    PlayerBan playerBan = room.removeBan(cn.currentPacket.readInt());
    if (playerBan == null) {}
  }
}


