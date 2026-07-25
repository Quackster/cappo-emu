package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;

public class ModMessageParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    PlayerData client = Clients.getPlayerData(cn.currentPacket.readInt());
    if (client == null) {
      return;
    }
    if (client.connection != null) {
      Utils.AlertFromHotel(client.connection.socket, cn.currentPacket.readString());
    }
  }
}


