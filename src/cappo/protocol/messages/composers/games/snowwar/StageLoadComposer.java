package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class StageLoadComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter();
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


