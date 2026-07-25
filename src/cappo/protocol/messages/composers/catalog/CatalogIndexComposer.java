package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.game.catalog.Catalog;
import cappo.game.catalog.Catalog.CatalogPage;
import cappo.protocol.messages.Composer;
import java.util.List;
import java.util.Map;

public class CatalogIndexComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int rank, String catalogType)
  {
    MessageWriter message = new MessageWriter(100 + Catalog.pages.size() * 100);
    Composer.initPacket(HEADER, message);
    dumpCatalogTab(rank, (List)Catalog.catalogMap.get(Integer.valueOf(0)), message);
    Composer.add(Boolean.valueOf(false), message);
    Composer.add(catalogType, message);
    Composer.endPacket(message);
    return message;
  }
  
  private static int dumpCatalogTab(int rank, List<Catalog.CatalogPage> pageList, MessageWriter message)
  {
    int size = 0;
    for (Catalog.CatalogPage page : pageList) {
      if ((page.minRank <= rank) && (page.isVisible))
      {
        size++;
        
        Composer.add(Boolean.valueOf(page.isVisible), message);
        
        Composer.add(Integer.valueOf(page.IconImage), message);
        Composer.add(Integer.valueOf(page.pageId), message);
        Composer.add(page.pageName, message);
        Composer.add(page.caption, message);
        Composer.add(Integer.valueOf(0), message);
        if (Catalog.catalogMap.containsKey(Integer.valueOf(page.pageId)))
        {
          Composer.add(message.setSaved(Integer.valueOf(0)), message);
          message.writeSaved(Integer.valueOf(dumpCatalogTab(rank, (List)Catalog.catalogMap.get(Integer.valueOf(page.pageId)), message)));
        }
        else
        {
          Composer.add(Integer.valueOf(0), message);
        }
      }
    }
    return size;
  }
}


