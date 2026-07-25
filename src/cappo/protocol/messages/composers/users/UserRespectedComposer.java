package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserRespectedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Id, int respects)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Id), ClientMessage);
    Composer.add(Integer.valueOf(respects), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


