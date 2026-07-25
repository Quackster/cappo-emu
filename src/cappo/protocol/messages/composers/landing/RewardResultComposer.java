package cappo.protocol.messages.composers.landing;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RewardResultComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int result)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(result), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


