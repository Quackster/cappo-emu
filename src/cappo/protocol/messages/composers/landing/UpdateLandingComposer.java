package cappo.protocol.messages.composers.landing;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UpdateLandingComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String data, String code)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(data, ClientMessage);
    Composer.add(code, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


