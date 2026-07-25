package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomBanRemoved
{
  public static int HEADER;
  
  public static MessageWriter compose(int roomId, int userId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(roomId), ClientMessage);
    Composer.add(Integer.valueOf(userId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


