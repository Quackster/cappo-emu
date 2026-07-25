package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class HabboClubOffersComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    


    Composer.add(Integer.valueOf(0), ClientMessage);
    







    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


