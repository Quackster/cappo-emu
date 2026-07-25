package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ActivityPointsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int pixels, int diamonds)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(2), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(pixels), ClientMessage);
    Composer.add(Integer.valueOf(105), ClientMessage);
    Composer.add(Integer.valueOf(diamonds), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


