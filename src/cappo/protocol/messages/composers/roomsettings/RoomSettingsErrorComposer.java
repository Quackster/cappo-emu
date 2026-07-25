package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomSettingsErrorComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int roomId, int errorCode, String info)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(roomId), ClientMessage);
    Composer.add(Integer.valueOf(errorCode), ClientMessage);
    Composer.add(info, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


