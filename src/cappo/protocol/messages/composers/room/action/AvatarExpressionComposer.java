package cappo.protocol.messages.composers.room.action;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class AvatarExpressionComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int VirtualId, int Action)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(VirtualId), ClientMessage);
    Composer.add(Integer.valueOf(Action), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


