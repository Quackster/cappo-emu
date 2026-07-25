package cappo.protocol.messages.composers.room.session;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomReadyComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String ModelName, int RoomId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(ModelName, ClientMessage);
    Composer.add(Integer.valueOf(RoomId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


