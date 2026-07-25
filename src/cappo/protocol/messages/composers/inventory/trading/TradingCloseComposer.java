package cappo.protocol.messages.composers.inventory.trading;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class TradingCloseComposer
{
  public static final int CLOSE = 0;
  public static final int COMIT_ERROR = 1;
  public static int HEADER;
  
  public static final MessageWriter compose(int ownerId, int reason)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ownerId), ClientMessage);
    Composer.add(Integer.valueOf(reason), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


