package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UniqueLimitedItemSoldOutComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


