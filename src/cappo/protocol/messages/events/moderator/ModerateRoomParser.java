package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import java.util.Map;

public class ModerateRoomParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowRoomAlert()) {
      return;
    }
    Avatar avatar = cn.avatar;
    if (avatar == null) {
      return;
    }
    cn.currentPacket.readInt();
    int action = cn.currentPacket.readInt();
    String text = cn.currentPacket.readString();
    if (action < 2) {
      for (Avatar user : avatar.room.userList.values())
      {
        user.cn.playerData.cautions += 1;
        Utils.AlertFromHotel(user.cn.socket, text);
      }
    } else {
      for (Avatar user : avatar.room.userList.values()) {
        Utils.AlertFromHotel(user.cn.socket, text);
      }
    }
  }
}


