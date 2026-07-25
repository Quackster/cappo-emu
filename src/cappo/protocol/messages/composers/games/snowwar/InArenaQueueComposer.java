package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class InArenaQueueComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Position)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Position), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


