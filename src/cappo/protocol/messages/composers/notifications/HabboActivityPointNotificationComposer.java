package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class HabboActivityPointNotificationComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Total, int Ammount, int Type)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Total), ClientMessage);
    Composer.add(Integer.valueOf(Ammount), ClientMessage);
    Composer.add(Integer.valueOf(Type), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


