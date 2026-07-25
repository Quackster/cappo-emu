package cappo.protocol.messages.composers.userdefinedroomevents;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class WiredRewardNotificationComposer
{
  public static final int ERR1 = 0;
  public static final int ERR2 = 1;
  public static final int ERR3 = 2;
  public static final int ERR4 = 3;
  public static final int NON_LUCKY = 4;
  public static final int ERR6 = 5;
  public static final int REWARD_SUCCESS = 6;
  public static final int BADGE_RECEIVED = 7;
  public static int HEADER;
  
  public static final MessageWriter compose(int reason)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(reason), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


