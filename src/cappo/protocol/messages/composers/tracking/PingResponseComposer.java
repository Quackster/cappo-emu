package cappo.protocol.messages.composers.tracking;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class PingResponseComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int PingId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(PingId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


