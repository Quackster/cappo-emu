package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.Iterator;
import java.util.List;

public class RoomInviteErrorComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int ErrorCode, List<Integer> Failed)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ErrorCode), ClientMessage);
    Composer.add(Integer.valueOf(Failed.size()), ClientMessage);
    for (Iterator localIterator = Failed.iterator(); localIterator.hasNext();)
    {
      int UserId = ((Integer)localIterator.next()).intValue();
      Composer.add(Integer.valueOf(UserId), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


