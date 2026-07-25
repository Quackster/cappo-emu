package cappo.protocol.messages.composers.help;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class CallForHelpOpenComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter(20);
      Composer.initPacket(HEADER, ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


