package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;

public class ModeratorActionParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    int UserId = cn.currentPacket.readInt();
    String Message = cn.currentPacket.readString();
    
    PlayerData client = Clients.getPlayerData(UserId);
    if (client == null) {
      return;
    }
    client.cautions += 1;
    if (client.connection != null) {
      Utils.AlertFromHotel(client.connection.socket, Message);
    }
  }
}


