package cappo.protocol.messages.composers.inventory.trading;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class TradingAlreadyOpenComposer
{
  public static final int TRD_HOTEL_DISABLED = 1;
  public static final int TRD_ACCOUNT_DISABLED = 2;
  public static final int TRD_PENDING_ASD = 4;
  public static final int TRD_ROOM_DISABLED = 6;
  public static final int TRD_SELF_TRADING = 7;
  public static final int TRD_OTHER_TRADING = 8;
  public static int HEADER;
  
  public static final MessageWriter compose(int errorCode, String otherUser)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(errorCode), ClientMessage);
    Composer.add(otherUser, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


