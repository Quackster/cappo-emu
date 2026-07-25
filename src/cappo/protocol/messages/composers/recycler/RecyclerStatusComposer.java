package cappo.protocol.messages.composers.recycler;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RecyclerStatusComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Status, int TimeToWait)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Status), ClientMessage);
    Composer.add(Integer.valueOf(TimeToWait), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


