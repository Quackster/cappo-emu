package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.Composer;

public class ScrUserInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Connection Main, int Type)
  {
    int TotalDaysLeft = 1;
    
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add("habbo_club", ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(Type), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


