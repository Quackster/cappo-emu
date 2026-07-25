package cappo.protocol.messages.composers.serializers;
import cappo.game.catalog.Catalog;

import cappo.engine.network.MessageWriter;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.game.catalog.Catalog.CatalogSubItem;
import cappo.game.collections.BaseItem;
import cappo.protocol.messages.Composer;

public class SerializeCatalogSubItem
{
  public static void parse(MessageWriter ClientMessage, Catalog.CatalogSubItem subItem, Catalog.CatalogProduct item)
  {
    Composer.add(subItem.baseItem.Type, ClientMessage);
    if (subItem.baseItem.Type.equals("b"))
    {
      Composer.add(subItem.extraData, ClientMessage);
    }
    else
    {
      Composer.add(Integer.valueOf(subItem.baseItem.SpriteId), ClientMessage);
      Composer.add(subItem.extraData, ClientMessage);
      Composer.add(subItem.amount, ClientMessage);
      if (item.uniqueLimitedItemsLaunched.intValue() < 1)
      {
        Composer.add(Boolean.valueOf(false), ClientMessage);
      }
      else
      {
        Composer.add(Boolean.valueOf(true), ClientMessage);
        Composer.add(item.uniqueLimitedItemsLaunched, ClientMessage);
        Composer.add(item.uniqueLimitedItemsLeft, ClientMessage);
      }
    }
  }
}
