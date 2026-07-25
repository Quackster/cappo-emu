package cappo.protocol.messages.composers.moderation;

import cappo.engine.network.MessageWriter;
import cappo.game.moderation.tickets.HelpTicket;
import cappo.game.moderation.tickets.HelpTicketsManager;
import cappo.protocol.messages.Composer;
import java.util.Map;

public class ModeratorInitComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter(1000 + 300 * HelpTicketsManager.tickets.size());
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(HelpTicketsManager.tickets.size()), ClientMessage);
    for (HelpTicket ticket : HelpTicketsManager.tickets.values()) {
      IssueInfoComposer.serializeIssue(ticket, ClientMessage);
    }
    Composer.add(Integer.valueOf(3), ClientMessage);
    
    Composer.add("mensaje predeterminado 1", ClientMessage);
    Composer.add("mensaje predeterminado 2", ClientMessage);
    Composer.add("mensaje predeterminado 3", ClientMessage);
    

    Composer.add(Integer.valueOf(1), ClientMessage);
    
    Composer.add("Acoso Sexual", ClientMessage);
    
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Integer.valueOf(2), ClientMessage);
    
    Composer.add("Me habbo violo", ClientMessage);
    Composer.add("Tonterias que se mandaran...", ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add("", ClientMessage);
    

    Composer.add("Pidio Sexo", ClientMessage);
    Composer.add("Tonterias que se mandaran...", ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add("", ClientMessage);
    


    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    
    Composer.add(Integer.valueOf(4), ClientMessage);
    
    Composer.add("Test template 1", ClientMessage);
    Composer.add("Test template 2", ClientMessage);
    Composer.add("Test template 3", ClientMessage);
    Composer.add("Test template 4", ClientMessage);
    

    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


