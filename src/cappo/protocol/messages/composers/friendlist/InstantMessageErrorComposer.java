package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class InstantMessageErrorComposer
{
  public static final int ERROR_RECEIVER_MUTED = 3;
  public static final int ERROR_SENDER_MUTED = 4;
  public static final int ERROR_SENDER_OFFLINE = 5;
  public static final int ERROR_SENDER_NOTFRIEND = 6;
  public static final int ERROR_SENDER_BUSY = 7;
  public static final int ERROR_SENDER_OFFLINE_FAILED = 10;
  public static int HEADER;
  
  public static final MessageWriter compose(int errorCode, int userId, String errorMessage)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(errorCode), ClientMessage);
    Composer.add(Integer.valueOf(userId), ClientMessage);
    Composer.add(errorMessage, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


