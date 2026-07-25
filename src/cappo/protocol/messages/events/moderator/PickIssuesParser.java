package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.moderation.StaffManager;
import cappo.game.moderation.tickets.HelpTicket;
import cappo.game.moderation.tickets.HelpTicketsManager;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.moderation.IssueInfoComposer;
import cappo.protocol.messages.composers.moderation.IssuePickFailedComposer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PickIssuesParser
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
    List<HelpTicket> failed = new ArrayList();
    for (int i = 0; i < count; i++)
    {
      int ticketId = cn.currentPacket.readInt();
      HelpTicket ticket = (HelpTicket)HelpTicketsManager.tickets.get(Integer.valueOf(ticketId));
      if (ticket != null) {
        if (ticket.status != 1)
        {
          failed.add(ticket);
        }
        else
        {
          ticket.status = 2;
          ticket.handlerId = cn.playerData.userId;
          ticket.handlerName = cn.playerData.userName;
          
          StaffManager.broadcast(IssueInfoComposer.compose(ticket));
        }
      }
    }
    cn.currentPacket.readBoolean();
    cn.currentPacket.readInt();
    if (!failed.isEmpty()) {
      QueueWriter.write(cn.socket, IssuePickFailedComposer.compose(failed));
    }
  }
}


