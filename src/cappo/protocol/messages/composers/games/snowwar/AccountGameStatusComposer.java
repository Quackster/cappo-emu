package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class AccountGameStatusComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int gameTypeId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(gameTypeId), ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


