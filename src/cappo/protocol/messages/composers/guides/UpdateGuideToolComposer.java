package cappo.protocol.messages.composers.guides;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UpdateGuideToolComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(boolean onDuty, int helpers, int guardians)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Boolean.valueOf(onDuty), ClientMessage);
    Composer.add(Integer.valueOf(helpers), ClientMessage);
    Composer.add(Integer.valueOf(guardians), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


