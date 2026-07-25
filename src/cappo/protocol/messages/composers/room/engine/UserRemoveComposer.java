package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserRemoveComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int VirtualId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.toString(VirtualId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


