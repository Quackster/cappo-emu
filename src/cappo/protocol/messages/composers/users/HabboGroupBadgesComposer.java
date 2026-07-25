package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class HabboGroupBadgesComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


