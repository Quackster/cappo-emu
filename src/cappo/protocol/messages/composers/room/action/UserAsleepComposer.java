package cappo.protocol.messages.composers.room.action;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserAsleepComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int VirtualId, Boolean IsAsleep)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(VirtualId), ClientMessage);
    Composer.add(IsAsleep, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


