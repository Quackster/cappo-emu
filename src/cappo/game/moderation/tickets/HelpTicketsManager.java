package cappo.game.moderation.tickets;

import cappo.engine.Server;
import cappo.game.moderation.StaffManager;
import cappo.protocol.messages.composers.moderation.IssueInfoComposer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HelpTicketsManager
{
  public static int ticketCount = 1;
  public static final Map<Integer, HelpTicket> tickets = new ConcurrentHashMap(100);
  
  public static void addTicket(HelpTicket ticket)
  {
    if (Server.blockTickets) {
      return;
    }
    ticket.status = 1;
    ticket.priority = 1;
    ticket.chatLogId = 1;
    ticket.timeStamp = System.currentTimeMillis();
    ticket.handlerName = "";
    
    ticket.id = (ticketCount++);
    tickets.put(Integer.valueOf(ticket.id), ticket);
    
    StaffManager.broadcast(IssueInfoComposer.compose(ticket));
  }
}


