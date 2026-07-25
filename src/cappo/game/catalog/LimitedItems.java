package cappo.game.catalog;

import cappo.game.collections.Utils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LimitedItems
{
  public Catalog.CatalogProduct product;
  public long startTime;
  public long endTime;
  public static LimitedItems nextLtd;
  public static Map<Integer, LimitedItems> items = new ConcurrentHashMap();
  
  public LimitedItems(Catalog.CatalogProduct item, long start, long end)
  {
    this.product = item;
    this.startTime = start;
    this.endTime = end;
  }
  
  public static void add(int productId, LimitedItems ltd)
  {
    items.put(Integer.valueOf(productId), ltd);
    long now = Utils.getTimestamp();
    if ((ltd.endTime > now) && (
      (nextLtd == null) || (nextLtd.startTime < ltd.startTime))) {
      nextLtd = ltd;
    }
  }
}


