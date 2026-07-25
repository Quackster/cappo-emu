package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomForwardComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(boolean isPublic, int roomId)
  {
    MessageWriter ClientMessage = new MessageWriter(500);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Boolean.valueOf(isPublic), ClientMessage);
    Composer.add(Integer.valueOf(roomId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


