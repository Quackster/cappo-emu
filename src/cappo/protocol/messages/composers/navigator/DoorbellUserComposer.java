package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class DoorbellUserComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String UserName)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(UserName, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


