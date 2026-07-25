package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserBlockedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int SnowWarBlockedGame)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(SnowWarBlockedGame), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


