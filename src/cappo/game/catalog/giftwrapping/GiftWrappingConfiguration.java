package cappo.game.catalog.giftwrapping;

import cappo.game.collections.BaseItem;
import java.util.HashMap;
import java.util.Map;

public class GiftWrappingConfiguration
{
  public static final boolean WRAPPING_ENABLED = true;
  public static final int WRAPPING_COST = 1;
  public static final int BOX_COUNT = 7;
  public static final int RIBBON_COUNT = 11;
  public static Map<Integer, BaseItem> baseGiftItems = new HashMap(20);
  public static Map<Integer, BaseItem> baseGiftFreeItems = new HashMap(10);
  public static boolean needUpdate;
  
  public static void addGift(BaseItem item)
  {
    int id = item.SpriteId;
    if (id < 1000) {
      baseGiftFreeItems.put(Integer.valueOf(id), item);
    } else {
      baseGiftItems.put(Integer.valueOf(id), item);
    }
    needUpdate = true;
  }
}


