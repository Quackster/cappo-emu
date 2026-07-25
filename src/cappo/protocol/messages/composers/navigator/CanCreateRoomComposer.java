package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class CanCreateRoomComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int ErrorCode, int MaxRooms)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ErrorCode), ClientMessage);
    Composer.add(Integer.valueOf(MaxRooms), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


