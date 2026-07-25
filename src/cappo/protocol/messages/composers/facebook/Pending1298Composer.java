package cappo.protocol.messages.composers.facebook;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class Pending1298Composer
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


