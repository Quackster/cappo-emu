package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.UnseenItems;
import cappo.protocol.messages.Composer;
import java.util.Iterator;
import java.util.List;

public class UnseenItemsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(UnseenItems Items)
  {
    MessageWriter ClientMessage = new MessageWriter(10000);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Items.Size), ClientMessage);
    for (int Type : new int[] { 1, 2, 3, 4, 5 })
    {
      List<Integer> items = (List)Items.unseenItems.get(Type);
      if (!items.isEmpty())
      {
        Composer.add(Integer.valueOf(Type), ClientMessage);
        Composer.add(Integer.valueOf(items.size()), ClientMessage);
        for (Iterator localIterator = items.iterator(); localIterator.hasNext();)
        {
          int ItemId = ((Integer)localIterator.next()).intValue();
          Composer.add(Integer.valueOf(ItemId), ClientMessage);
        }
      }
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


