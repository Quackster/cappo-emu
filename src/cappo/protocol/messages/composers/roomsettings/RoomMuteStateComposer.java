package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomMuteStateComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(boolean enabled)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Boolean.valueOf(enabled), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


