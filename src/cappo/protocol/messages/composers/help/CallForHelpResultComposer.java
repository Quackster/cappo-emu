package cappo.protocol.messages.composers.help;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class CallForHelpResultComposer
{
  public static int HEADER;
  public static final int SENT_OK = 0;
  public static final int ERROR_TOO_MANY_PENDING = 1;
  public static final int HAS_ABUSIVE_CALL = 2;
  
  public static final MessageWriter compose(int result)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(result), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


