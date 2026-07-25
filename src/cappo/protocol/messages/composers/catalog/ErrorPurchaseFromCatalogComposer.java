package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ErrorPurchaseFromCatalogComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int ErrorCode)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ErrorCode), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


