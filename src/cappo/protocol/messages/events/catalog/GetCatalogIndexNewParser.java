package cappo.protocol.messages.events.catalog;

import cappo.engine.network.MessageReader;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.catalog.Catalog;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.catalog.CatalogIndexComposer;
import java.util.Map;

public class GetCatalogIndexNewParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (Catalog.isBlocked) {
      return;
    }
    String catalogType = cn.currentPacket.readString();
    
    MessageWriter response = (MessageWriter)Catalog.indexMap.get(Integer.valueOf(cn.playerData.staffLevel));
    if (response == null)
    {
      response = CatalogIndexComposer.compose(cn.playerData.staffLevel, catalogType);
      Catalog.indexMap.put(Integer.valueOf(cn.playerData.staffLevel), response);
    }
    QueueWriter.write(cn.socket, response);
  }
}


