package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.moderation.StaffManager;
import cappo.game.moderation.tickets.HelpTicket;
import cappo.game.moderation.tickets.HelpTicketsManager;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.help.IssueCloseNotificationComposer;
import cappo.protocol.messages.composers.moderation.IssueInfoComposer;
import java.util.Map;

public class CloseIssuesParser
  extends IncomingMessageEvent
{
  public static final int CLOSE_USELESS = 1;
  public static final int CLOSE_ABUSIVE = 2;
  public static final int CLOSE_RESOLVED = 3;
  
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    int action = cn.currentPacket.readInt();
    
    int count = cn.currentPacket.readInt();
    if ((count < 1) || (count > 20)) {
      return;
    }
    MessageWriter packet;
    if (action == 1)
    {
      packet = IssueCloseNotificationComposer.compose(1);
    }
    else
    {
      if (action == 2) {
        packet = IssueCloseNotificationComposer.compose(2);
      } else {
        packet = IssueCloseNotificationComposer.compose(0);
      }
    }
    for (int i = 0; i < count; i++)
    {
      int ticketId = cn.currentPacket.readInt();
      HelpTicket ticket = (HelpTicket)HelpTicketsManager.tickets.get(Integer.valueOf(ticketId));
      if ((ticket != null) && (ticket.status == 2) && (ticket.handlerId == cn.playerData.userId))
      {
        ticket.status = 3;
        StaffManager.broadcast(IssueInfoComposer.compose(ticket));
        
        HelpTicketsManager.tickets.remove(Integer.valueOf(ticketId));
        
        PlayerData client = Clients.getPlayerData(ticket.reporterId);
        if ((client != null) && (client.connection != null)) {
          QueueWriter.writeAndFlush(client.connection.socket, packet);
        }
      }
    }
  }
}
