package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.moderation.ModeratorRoomInfoComposer;

public class GetModeratorRoomInfoParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    QueueWriter.write(cn.socket, ModeratorRoomInfoComposer.compose(cn.currentPacket.readInt()));
  }
}


