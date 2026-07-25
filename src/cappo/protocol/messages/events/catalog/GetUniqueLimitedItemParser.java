package cappo.protocol.messages.events.catalog;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.catalog.Catalog;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.catalog.UniqueLimitedItemComposer;
import cappo.protocol.messages.composers.catalog.UniqueLimitedItemSoldOutComposer;
import java.util.Map;

public class GetUniqueLimitedItemParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Catalog.CatalogProduct item = (Catalog.CatalogProduct)Catalog.Items.get(Integer.valueOf(Main.currentPacket.readInt()));
    if (item == null) {
      return;
    }
    QueueWriter.write(Main.socket, UniqueLimitedItemComposer.compose(item));
    if (item.uniqueLimitedItemsLeft.intValue() < 1) {
      QueueWriter.write(Main.socket, UniqueLimitedItemSoldOutComposer.compose());
    }
  }
}


