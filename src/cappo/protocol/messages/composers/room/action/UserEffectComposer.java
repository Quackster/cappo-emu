package cappo.protocol.messages.composers.room.action;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserEffectComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int VirtualId, int CurrentEffect)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(VirtualId), ClientMessage);
    Composer.add(Integer.valueOf(CurrentEffect), ClientMessage);
    Composer.add(Integer.valueOf(99), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


