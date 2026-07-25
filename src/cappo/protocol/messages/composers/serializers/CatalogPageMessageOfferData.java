package cappo.protocol.messages.composers.serializers;
import cappo.game.catalog.Catalog;

import cappo.engine.network.MessageWriter;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.game.catalog.Catalog.CatalogSubItem;
import cappo.protocol.messages.Composer;
import java.util.List;

public class CatalogPageMessageOfferData
{
  public static void parse(MessageWriter ClientMessage, Catalog.CatalogProduct item)
  {
    Composer.add(Integer.valueOf(item.productId), ClientMessage);
    Composer.add(item.itemName, ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(item.creditCost), ClientMessage);
    Composer.add(Integer.valueOf(item.activityPointCost), ClientMessage);
    Composer.add(Integer.valueOf(item.activityPointsType), ClientMessage);
    Composer.add(Boolean.valueOf(item.AllowGift), ClientMessage);
    Composer.add(Integer.valueOf(item.items.size()), ClientMessage);
    for (Catalog.CatalogSubItem subItem : item.items) {
      SerializeCatalogSubItem.parse(ClientMessage, subItem, item);
    }
    Composer.add(Integer.valueOf(item.clubLevel), ClientMessage);
    Composer.add(Boolean.valueOf(item.allowBundleDiscounts), ClientMessage);
  }
}
