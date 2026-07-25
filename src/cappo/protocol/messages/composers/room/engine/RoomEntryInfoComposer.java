package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomEntryInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Boolean isPrivate, int RoomId, Boolean isOwner)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(isPrivate, ClientMessage);
    Composer.add(Integer.valueOf(RoomId), ClientMessage);
    Composer.add(isOwner, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


