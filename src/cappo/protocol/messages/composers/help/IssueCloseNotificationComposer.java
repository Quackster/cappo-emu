package cappo.protocol.messages.composers.help;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class IssueCloseNotificationComposer
{
  public static final int RESOLVED = 0;
  public static final int USELESS = 1;
  public static final int ABUSIVE = 2;
  public static int HEADER;
  
  public static final MessageWriter compose(int reason)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(reason), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


