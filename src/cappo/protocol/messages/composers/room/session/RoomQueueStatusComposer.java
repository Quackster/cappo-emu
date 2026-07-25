package cappo.protocol.messages.composers.room.session;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomQueueStatusComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String ModelName, int RoomId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


