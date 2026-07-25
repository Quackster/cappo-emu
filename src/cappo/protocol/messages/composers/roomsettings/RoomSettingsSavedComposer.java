package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomSettingsSavedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int RoomId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(RoomId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


