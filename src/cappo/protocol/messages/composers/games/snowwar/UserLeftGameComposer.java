package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserLeftGameComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int PlayerId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(PlayerId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


