package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class BuilderBuyCountComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter message = new MessageWriter();
    Composer.initPacket(HEADER, message);
    Composer.writeInt32(0, message);
    Composer.endPacket(message);
    return message;
  }
}


