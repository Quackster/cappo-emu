package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class FlatAccessDeniedComposer
{
  public static final int GUEST_ROOM_FULL = 1;
  public static final int UNKNOWN = 2;
  public static final int CUSTOM_ERROR = 3;
  public static final int BANNED = 4;
  public static int HEADER;
  
  public static final MessageWriter compose(int reason, String errorCode)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(reason), ClientMessage);
    if (reason == 3) {
      Composer.add(errorCode, ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


