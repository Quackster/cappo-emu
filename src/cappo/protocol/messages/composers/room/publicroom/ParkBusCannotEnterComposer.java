package cappo.protocol.messages.composers.room.publicroom;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ParkBusCannotEnterComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String reason)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(reason, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


