package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import java.util.Map;

public class ModeratorRoomActionParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    RoomData room = RoomManager.getRoom(cn.currentPacket.readInt());
    if (room == null) {
      return;
    }
    if (cn.currentPacket.readInt() == 1) {
      room.state = 1;
    }
    if (cn.currentPacket.readInt() == 1)
    {
      room.name = "Inappropriate to Hotel Management";
      room.description = "Inappropriate to Hotel Management";
      room.tags = new String[0];
    }
    if ((cn.currentPacket.readInt() == 1) && 
      (room.room != null)) {
      for (Avatar user : room.room.userList.values()) {
        room.room.removeUserFromRoom(user.cn, true, false);
      }
    }
  }
}


