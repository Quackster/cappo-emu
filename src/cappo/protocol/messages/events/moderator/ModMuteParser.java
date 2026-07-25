package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.moderation.UserMuted;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;

public class ModMuteParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    PlayerData client = Clients.getPlayerDataLoaded(cn.currentPacket.readInt());
    if ((client == null) || (client.connection == null)) {
      return;
    }
    Connection plrConnection = client.connection;
    
    plrConnection.userMuted = new UserMuted();
    plrConnection.userMuted.reason = cn.currentPacket.readString();
    plrConnection.userMuted.unMuteTimeStamp = (Utils.getTimestamp() + cn.currentPacket.readInt());
    
    String ticketMessage = cn.currentPacket.readString();
    if (!ticketMessage.isEmpty()) {
      cn.currentPacket.readInt();
    }
  }
}


