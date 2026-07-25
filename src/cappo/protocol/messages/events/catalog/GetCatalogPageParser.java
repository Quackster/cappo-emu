package cappo.protocol.messages.events.catalog;

import cappo.engine.network.MessageReader;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.catalog.Catalog;
import cappo.game.catalog.Catalog.CatalogPage;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.catalog.CatalogPageComposer;
import java.util.Map;

public class GetCatalogPageParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    Catalog.CatalogPage page = (Catalog.CatalogPage)Catalog.pages.get(Integer.valueOf(cn.currentPacket.readInt()));
    if ((Catalog.isBlocked) || (page == null) || (!page.isEnabled)) {
      return;
    }
    int offerId = cn.currentPacket.readInt();
    String catalogType = cn.currentPacket.readString();
    if ((page.isCacheDisabled) || (offerId != -1) || (!catalogType.equals("NORMAL")))
    {
      QueueWriter.write(cn.socket, CatalogPageComposer.compose(page, offerId, catalogType));
      return;
    }
    MessageWriter response = (MessageWriter)Catalog.pageMap.get(Integer.valueOf(page.pageId));
    if (response == null)
    {
      response = CatalogPageComposer.compose(page, -1, "NORMAL");
      Catalog.pageMap.put(Integer.valueOf(page.pageId), response);
    }
    QueueWriter.write(cn.socket, response);
  }
}


