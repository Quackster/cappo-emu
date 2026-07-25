package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class HabboBroadcastComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String Text)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Text, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


