package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeFlatController;

public class FlatControllerAddedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int roomId, int clientId, String userName)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(roomId), ClientMessage);
    SerializeFlatController.parse(ClientMessage, clientId, userName);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


