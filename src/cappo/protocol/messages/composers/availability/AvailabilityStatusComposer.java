package cappo.protocol.messages.composers.availability;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class AvailabilityStatusComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter(8);
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Boolean.valueOf(true), ClientMessage);
      Composer.add(Boolean.valueOf(false), ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


