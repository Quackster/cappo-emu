package cappo.protocol.messages.composers.moderation;

import cappo.engine.network.MessageWriter;
import cappo.game.moderation.tickets.HelpTicket;
import cappo.protocol.messages.Composer;

public class IssueInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(HelpTicket ticket)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    serializeIssue(ticket, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
  
  public static final void serializeIssue(HelpTicket ticket, MessageWriter ClientMessage)
  {
    Composer.add(Integer.valueOf(ticket.id), ClientMessage);
    Composer.add(Short.valueOf(ticket.status), ClientMessage);
    Composer.add(Short.valueOf(ticket.type), ClientMessage);
    Composer.add(Short.valueOf(ticket.category), ClientMessage);
    Composer.add(Long.valueOf(System.currentTimeMillis() - ticket.timeStamp), ClientMessage);
    Composer.add(Short.valueOf(ticket.priority), ClientMessage);
    Composer.add(Integer.valueOf(ticket.reporterId), ClientMessage);
    Composer.add(ticket.reporterName, ClientMessage);
    Composer.add(Integer.valueOf(ticket.reportedId), ClientMessage);
    Composer.add(ticket.reportedName, ClientMessage);
    Composer.add(Integer.valueOf(ticket.handlerId), ClientMessage);
    Composer.add(ticket.handlerName, ClientMessage);
    Composer.add(ticket.text, ClientMessage);
    Composer.add(Integer.valueOf(ticket.chatLogId), ClientMessage);
    Composer.add(ticket.roomName, ClientMessage);
    Composer.add(Integer.valueOf(ticket.roomType), ClientMessage);
    if (ticket.roomType == 1)
    {
      Composer.add("", ClientMessage);
      Composer.add(Integer.valueOf(ticket.roomId), ClientMessage);
      Composer.add("", ClientMessage);
    }
    else
    {
      Composer.add("", ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
    }
    Composer.add(Integer.valueOf(0), ClientMessage);
  }
}


