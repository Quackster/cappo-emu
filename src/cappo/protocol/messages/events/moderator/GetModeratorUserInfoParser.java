package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.moderation.ModeratorUserInfoComposer;

public class GetModeratorUserInfoParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    int id = cn.currentPacket.readInt();
    PlayerData Client = Clients.getPlayerData(id);
    if (Client == null) {
      return;
    }
    QueueWriter.write(cn.socket, ModeratorUserInfoComposer.compose(Client));
  }
}


