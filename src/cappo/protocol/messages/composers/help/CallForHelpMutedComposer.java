package cappo.protocol.messages.composers.help;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class CallForHelpMutedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String message)
  {
    MessageWriter ClientMessage = new MessageWriter(200);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(message, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


