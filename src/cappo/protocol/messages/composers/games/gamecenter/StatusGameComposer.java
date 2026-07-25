package cappo.protocol.messages.composers.games.gamecenter;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class StatusGameComposer
{
  public static final int OK = 0;
  public static final int CLOSED = 1;
  public static int HEADER;
  
  public static final MessageWriter compose(int gameTypeId, int status)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(gameTypeId), ClientMessage);
    Composer.add(Integer.valueOf(status), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


