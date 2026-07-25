package cappo.protocol.messages.events.room.furniture;
import cappo.game.catalog.Catalog;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.PresentItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.events.catalog.PurchaseFromCatalogParser;

public class PresentOpenParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    PresentItem gift = (PresentItem)avatar.room.getFloorItem(Main.currentPacket.readInt());
    if (gift == null) {
      return;
    }
    avatar.room.removeFloorItem(gift, Main.playerData.userId);
    gift.setMysqlState(4);
    
    Catalog.CatalogProduct giveItem = gift.getProduct();
    if (giveItem == null) {
      return;
    }
    PurchaseFromCatalogParser.buyProduct(giveItem, gift.getProductParam(), 1, Main);
  }
}
