package cappo.protocol.messages.composers.landing;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class BadgeButtonStatusComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String badge, boolean hidde)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(badge, ClientMessage);
    Composer.add(Boolean.valueOf(hidde), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


