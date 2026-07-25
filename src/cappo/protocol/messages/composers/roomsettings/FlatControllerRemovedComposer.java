package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class FlatControllerRemovedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int roomId, int clientId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(roomId), ClientMessage);
    Composer.add(Integer.valueOf(clientId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


