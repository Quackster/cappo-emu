package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.moderation.StaffManager;
import cappo.game.moderation.tickets.HelpTicket;
import cappo.game.moderation.tickets.HelpTicketsManager;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.moderation.IssueInfoComposer;
import java.util.Map;

public class ReleaseIssuesParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    int count = cn.currentPacket.readInt();
    if ((count < 1) || (count > 20)) {
      return;
    }
    for (int i = 0; i < count; i++)
    {
      int ticketId = cn.currentPacket.readInt();
      HelpTicket ticket = (HelpTicket)HelpTicketsManager.tickets.get(Integer.valueOf(ticketId));
      if ((ticket != null) && (ticket.status == 2) && (ticket.handlerId == cn.playerData.userId))
      {
        ticket.status = 1;
        ticket.handlerId = 0;
        ticket.handlerName = "";
        
        StaffManager.broadcast(IssueInfoComposer.compose(ticket));
      }
    }
  }
}


