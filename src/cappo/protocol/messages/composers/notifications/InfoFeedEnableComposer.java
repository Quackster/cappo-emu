package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class InfoFeedEnableComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter(7);
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Boolean.valueOf(true), ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


