package cappo.protocol.messages.composers.catalog;
import cappo.game.catalog.Catalog;

import cappo.engine.network.MessageWriter;
import cappo.game.catalog.Catalog.CatalogPage;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.CatalogPageMessageOfferData;
import cappo.protocol.messages.composers.serializers.SerializeCatalogPageData;
import java.util.List;

public class CatalogPageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Catalog.CatalogPage Page, int offerId, String catalogType)
  {
    MessageWriter ClientMessage = new MessageWriter(50000);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Page.pageId), ClientMessage);
    Composer.add(Page.Layout, ClientMessage);
    SerializeCatalogPageData.parse(ClientMessage, Page.PageData);
    Composer.add(Integer.valueOf(Page.items.size()), ClientMessage);
    for (Catalog.CatalogProduct item : Page.items) {
      CatalogPageMessageOfferData.parse(ClientMessage, item);
    }
    Composer.add(Integer.valueOf(offerId), ClientMessage);
    Composer.add(Boolean.valueOf(Page.acceptSeasonCurrencyAsCredits), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}
