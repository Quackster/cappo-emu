package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class StageRunningComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose(int seconds)
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter();
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Integer.valueOf(seconds), ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


