package cappo.protocol.messages.events.roomsettings;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.settings.ModerationPermissions;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.roomsettings.RoomMuteStateComposer;

public class SetRoomMuteStateParser
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
    roomData.muteAllOn = (!roomData.muteAllOn);
    room.sendMessage(RoomMuteStateComposer.compose(roomData.muteAllOn), new int[] { 4, 5 });
  }
}


