package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ErrorBuyComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Boolean isCredits, Boolean isActivityPoint, int activityPointType)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(isCredits, ClientMessage);
    Composer.add(isActivityPoint, ClientMessage);
    if (isActivityPoint.booleanValue()) {
      Composer.add(Integer.valueOf(activityPointType), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


