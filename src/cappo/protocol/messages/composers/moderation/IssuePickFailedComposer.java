package cappo.protocol.messages.composers.moderation;

import cappo.engine.network.MessageWriter;
import cappo.game.moderation.tickets.HelpTicket;
import cappo.protocol.messages.Composer;
import java.util.List;

public class IssuePickFailedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(List<HelpTicket> tickets)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(tickets.size()), ClientMessage);
    for (HelpTicket ticket : tickets)
    {
      Composer.add(Integer.valueOf(ticket.id), ClientMessage);
      Composer.add(Integer.valueOf(ticket.handlerId), ClientMessage);
      Composer.add(ticket.handlerName, ClientMessage);
    }
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


