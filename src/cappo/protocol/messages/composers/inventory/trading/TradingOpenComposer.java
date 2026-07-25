package cappo.protocol.messages.composers.inventory.trading;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class TradingOpenComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int inviter, int receiver)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(inviter), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(receiver), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


