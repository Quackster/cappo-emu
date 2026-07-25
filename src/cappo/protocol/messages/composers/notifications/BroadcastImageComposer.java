package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class BroadcastImageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String uri)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(uri, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


