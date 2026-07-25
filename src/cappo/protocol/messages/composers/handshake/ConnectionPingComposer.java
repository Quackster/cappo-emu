package cappo.protocol.messages.composers.handshake;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ConnectionPingComposer
{
  private static MessageWriter ClientMessage;
  public static int HEADER;
  
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


