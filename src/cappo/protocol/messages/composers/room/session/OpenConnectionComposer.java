package cappo.protocol.messages.composers.room.session;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class OpenConnectionComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter();
      Composer.initPacket(HEADER, ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


