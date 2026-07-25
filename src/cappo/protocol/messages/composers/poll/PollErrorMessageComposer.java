package cappo.protocol.messages.composers.poll;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class PollErrorMessageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


