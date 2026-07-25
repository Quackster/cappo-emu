package cappo.protocol.messages.composers.landing;

import cappo.engine.network.MessageWriter;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.game.catalog.LimitedItems;
import cappo.game.collections.Utils;
import cappo.protocol.messages.Composer;

public class NextLimitedAvailableComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    if (LimitedItems.nextLtd == null)
    {
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Integer.valueOf(-1), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add("", ClientMessage);
    }
    else
    {
      long now = Utils.getTimestamp();
      if (LimitedItems.nextLtd.startTime > now)
      {
        Composer.add(Long.valueOf(LimitedItems.nextLtd.startTime - now), ClientMessage);
        Composer.add(Integer.valueOf(-1), ClientMessage);
      }
      else
      {
        Composer.add(Integer.valueOf(0), ClientMessage);
        Composer.add(Integer.valueOf(LimitedItems.nextLtd.product.pageId), ClientMessage);
      }
      Composer.add(Integer.valueOf(LimitedItems.nextLtd.product.productId), ClientMessage);
      Composer.add(LimitedItems.nextLtd.product.itemName, ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


