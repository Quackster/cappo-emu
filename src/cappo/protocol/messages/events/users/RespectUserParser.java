package cappo.protocol.messages.events.users;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.action.AvatarExpressionComposer;
import cappo.protocol.messages.composers.users.UserRespectedComposer;

public class RespectUserParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if (Main.dailyRespectPoints < 1) {
      return;
    }
    RoomTask room = avatar.room;
    

    Avatar User = room.getRoomUserById(Main.currentPacket.readInt());
    if (User != null)
    {
      room.sendMessage(UserRespectedComposer.compose(User.id, ++User.cn.respects));
      room.sendMessage(AvatarExpressionComposer.compose(avatar.virtualId, 7));
      Main.dailyRespectPoints -= 1;
    }
  }
}


