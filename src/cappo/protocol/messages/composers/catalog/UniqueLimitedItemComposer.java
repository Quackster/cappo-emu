package cappo.protocol.messages.composers.catalog;
import cappo.game.catalog.Catalog;

import cappo.engine.network.MessageWriter;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.CatalogPageMessageOfferData;

public class UniqueLimitedItemComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Catalog.CatalogProduct item)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    CatalogPageMessageOfferData.parse(ClientMessage, item);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}
