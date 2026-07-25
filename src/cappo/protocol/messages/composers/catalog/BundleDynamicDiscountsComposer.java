package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class BundleDynamicDiscountsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(50), ClientMessage);
    Composer.add(Integer.valueOf(5), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    


    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


