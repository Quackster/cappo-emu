package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomPropertyComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String Type, String Value)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Type, ClientMessage);
    Composer.add(Value, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


