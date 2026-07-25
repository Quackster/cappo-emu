package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class FollowFriendFailedComposer
{
  public static final int NOT_FRIEND = 0;
  public static final int OFFLINE = 1;
  public static final int HOTEL_VIEW = 2;
  public static final int PREVENTED = 3;
  public static int HEADER;
  
  public static final MessageWriter compose(int ErrorCode)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ErrorCode), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


