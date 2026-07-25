package cappo.protocol.messages.composers.inventory.trading;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class TradingAcceptComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int userId, int state)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(userId), ClientMessage);
    Composer.add(Integer.valueOf(state), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


