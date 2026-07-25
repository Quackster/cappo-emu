package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.Iterator;
import java.util.Set;

public class FavouritesComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Set<Integer> Favorite_Rooms)
  {
    MessageWriter ClientMessage = new MessageWriter(20 + Favorite_Rooms.size() * 250);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(30), ClientMessage);
    Composer.add(Integer.valueOf(Favorite_Rooms.size()), ClientMessage);
    for (Iterator localIterator = Favorite_Rooms.iterator(); localIterator.hasNext();)
    {
      int roomId = ((Integer)localIterator.next()).intValue();
      Composer.add(Integer.valueOf(roomId), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


