package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class NotifyUserNameChangeComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int id, int virtualId, String name)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(id), ClientMessage);
    Composer.add(Integer.valueOf(virtualId), ClientMessage);
    Composer.add(name, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


