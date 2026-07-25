package cappo.protocol.messages.events.moderator;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.handshake.UserDisconnectComposer;
import cappo.protocol.messages.events.handshake.SSOTicketParser;
import java.util.Map;

public class ModBanParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    PlayerData playerData = cn.getPlayerData();
    if (!playerData.allowBan()) {
      return;
    }
    PlayerData client = Clients.getPlayerData(cn.currentPacket.readInt());
    if (client == null) {
      return;
    }
    if ((client.staffLevel > 1) && (client.staffLevel >= playerData.staffLevel)) {
      return;
    }
    String reason = cn.currentPacket.readString();
    int hours = cn.currentPacket.readInt();
    String ticketMessage = cn.currentPacket.readString();
    boolean isAvatarBan = cn.currentPacket.readBoolean();
    int issueId = -1;
    if (!ticketMessage.isEmpty()) {
      issueId = cn.currentPacket.readInt();
    }
    long now = Utils.getTimestamp();
    if (hours == 100000) {
      SSOTicketParser.temporallyBans.put(Integer.valueOf(client.userId), Integer.valueOf(2147483646));
    } else {
      SSOTicketParser.temporallyBans.put(Integer.valueOf(client.userId), Integer.valueOf((int)(now + hours * 3600)));
    }
    try
    {
      Database.exec(
      
        "INSERT INTO bans (type,reason,text,hours,created,mod_id,user_id,issue_id) VALUES('" + (isAvatarBan ? "avatar" : "account") + "',?,?," + hours + "," + now + "," + playerData.userId + "," + client.userId + "," + issueId + ")" + " ON DUPLICATE KEY UPDATE hours=" + hours + ",created=" + now + ";", new Object[] { reason, ticketMessage });
    }
    catch (Exception ex)
    {
      Log.printException("Disconnect", ex);
    }
    int Type = 1;
    if (hours == 100000) {
      Type = 10;
    }
    if (client.connection != null) {
      QueueWriter.writeAndClose(client.connection.socket, UserDisconnectComposer.compose(Type));
    }
  }
}


