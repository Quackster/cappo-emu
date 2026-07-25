package cappo.protocol.messages.composers.inventory.purse;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class CreditBalanceComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int credits)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.toString(credits), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


