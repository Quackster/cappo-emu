package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ClubGiftNotificationComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Total, int Ammount, int Type)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    


    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


