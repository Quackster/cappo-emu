package cappo.protocol.messages.composers.handshake;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class GenericErrorComposer
{
  public static int HEADER;
  public static final int ROOM_CANT_SET_NOT_OWNER = -32000;
  public static final int ROOM_KICKED = 4008;
  public static final int ROOM_UNUSED_YET = -13001;
  public static final int NAVIGATOR_WRONG_PASSWORD = -100002;
  public static final int NAVIGATOR_NEED_TO_BE_VIP = 4009;
  public static final int NAVIGATOR_INVALID_ROOM_NAME = 4010;
  public static final int LOGIN_AUTH_FAILED = -3;
  public static final int LOGIN_CONNECT_FAILED = -400;
  
  public static final MessageWriter compose(int ErrorCode)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ErrorCode), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


