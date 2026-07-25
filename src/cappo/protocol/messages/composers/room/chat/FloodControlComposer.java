package cappo.protocol.messages.composers.room.chat;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class FloodControlComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Time)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Time), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


