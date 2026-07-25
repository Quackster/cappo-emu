package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class FlatCreatedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int RoomId, String RoomName)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(RoomId), ClientMessage);
    Composer.add(RoomName, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


