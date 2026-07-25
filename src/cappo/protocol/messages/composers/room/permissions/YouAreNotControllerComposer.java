package cappo.protocol.messages.composers.room.permissions;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class YouAreNotControllerComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter(6);
      Composer.initPacket(HEADER, ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


