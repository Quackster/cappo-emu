package cappo.protocol.messages.composers.marketplace;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class MarketplaceCanMakeOfferComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Result, int ErrorCode)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Result), ClientMessage);
    Composer.add(Integer.valueOf(ErrorCode), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


