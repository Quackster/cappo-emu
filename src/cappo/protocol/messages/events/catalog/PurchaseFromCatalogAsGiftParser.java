package cappo.protocol.messages.events.catalog;

import cappo.engine.Server;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.catalog.Catalog;
import cappo.game.catalog.Catalog.CatalogPage;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.game.catalog.giftwrapping.GiftWrappingConfiguration;
import cappo.game.collections.BaseItem;
import cappo.game.collections.UnseenItems;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import cappo.game.roomengine.entity.item.extradata.StuffDataReader;
import cappo.game.roomengine.entity.item.extradata.StuffDataWriter;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.PresentItem;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.catalog.ErrorBuyComposer;
import cappo.protocol.messages.composers.catalog.ErrorPurchaseFromCatalogComposer;
import cappo.protocol.messages.composers.inventory.furni.FurniListUpdateComposer;
import cappo.protocol.messages.composers.inventory.purse.CreditBalanceComposer;
import cappo.protocol.messages.composers.notifications.BuyNotificationComposer;
import cappo.protocol.messages.composers.notifications.HabboActivityPointNotificationComposer;
import cappo.protocol.messages.composers.notifications.UnseenItemsComposer;
import java.util.Map;

public class PurchaseFromCatalogAsGiftParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int pageId = Main.currentPacket.readInt();
    int itemId = Main.currentPacket.readInt();
    String extraParam = Main.currentPacket.readString();
    String sendTo = Main.currentPacket.readString();
    String text = Main.currentPacket.readString();
    int boxSpriteId = Main.currentPacket.readInt();
    Main.currentPacket.readInt();
    Main.currentPacket.readInt();
    boolean showAvatar = Main.currentPacket.readBoolean();
    

    BaseItem boxBaseItem = (BaseItem)GiftWrappingConfiguration.baseGiftItems.get(Integer.valueOf(boxSpriteId));
    int wrappingCost;
    if (boxBaseItem == null)
    {
      boxBaseItem = (BaseItem)GiftWrappingConfiguration.baseGiftFreeItems.get(Integer.valueOf(boxSpriteId));
      if (boxBaseItem == null)
      {
        QueueWriter.write(Main.socket, ErrorPurchaseFromCatalogComposer.compose(0));
        return;
      }
      wrappingCost = 0;
    }
    else
    {
      wrappingCost = 1;
    }
    PlayerData plr = Clients.getPlayerData(sendTo);
    if (plr == null)
    {
      QueueWriter.write(Main.socket, ErrorPurchaseFromCatalogComposer.compose(0));
      return;
    }
    Catalog.CatalogPage page = (Catalog.CatalogPage)Catalog.pages.get(Integer.valueOf(pageId));
    if ((page == null) || (!page.isEnabled))
    {
      QueueWriter.write(Main.socket, ErrorPurchaseFromCatalogComposer.compose(0));
      return;
    }
    if (page.minRank > Main.playerData.staffLevel)
    {
      QueueWriter.write(Main.socket, ErrorPurchaseFromCatalogComposer.compose(0));
      return;
    }
    Catalog.CatalogProduct product = (Catalog.CatalogProduct)Catalog.Items.get(Integer.valueOf(itemId));
    if ((product == null) || (product.pageId != page.pageId)) {
      return;
    }
    if ((product.creditCost > 0) || (wrappingCost > 0))
    {
      int finalCost = product.creditCost + wrappingCost;
      if (Main.credits < finalCost)
      {
        QueueWriter.write(Main.socket, ErrorBuyComposer.compose(Boolean.valueOf(true), Boolean.valueOf(false), 0));
        return;
      }
      Main.credits -= finalCost;
      QueueWriter.write(Main.socket, CreditBalanceComposer.compose(Main.credits));
    }
    if (product.activityPointCost > 0)
    {
      int finalCost = product.activityPointCost;
      if (product.activityPointsType == 105)
      {
        if (Main.diamondAmmount < finalCost)
        {
          QueueWriter.write(Main.socket, ErrorBuyComposer.compose(Boolean.valueOf(false), Boolean.valueOf(true), 105));
          return;
        }
        Main.diamondAmmount -= finalCost;
        QueueWriter.write(Main.socket, HabboActivityPointNotificationComposer.compose(Main.diamondAmmount, 0, 105));
      }
      else
      {
        if (Main.pixelAmmount < finalCost)
        {
          QueueWriter.write(Main.socket, ErrorBuyComposer.compose(Boolean.valueOf(false), Boolean.valueOf(true), 0));
          return;
        }
        Main.pixelAmmount -= finalCost;
        QueueWriter.write(Main.socket, HabboActivityPointNotificationComposer.compose(Main.pixelAmmount, 0, 0));
      }
    }
    Connection clientCn = plr.connection;
    

    StuffDataWriter data = new StuffDataWriter(1);
    data.writeInt8(showAvatar ? 5 : 3);
    data.writeString("MESSAGE");
    data.writeString(text);
    data.writeString("PRODUCT_CODE");
    data.writeString(Integer.toString(product.productId));
    data.writeString("EXTRA_PARAM");
    data.writeString(extraParam);
    if (showAvatar)
    {
      data.writeString("PURCHASER_NAME");
      data.writeString(Main.playerData.userName);
      data.writeString("PURCHASER_FIGURE");
      data.writeString(Main.playerData.avatarLook.toString());
    }
    int refId = Server.generateRefItemId();
    
    FloorItem userItem = new PresentItem();
    userItem.refId = refId;
    userItem.itemId = Server.generateItemId();
    userItem.baseItem = boxBaseItem;
    userItem.owner = plr;
    userItem.extraData = new MapStuffData(new StuffDataReader(data.getData()));
    userItem.setMysqlState(3);
    if (clientCn != null)
    {
      clientCn.avatarData.UnseenItems.AddItem(1, refId);
      clientCn.inventoryAddFloorItem(userItem);
    }
    if (clientCn != null)
    {
      QueueWriter.write(clientCn.socket, FurniListUpdateComposer.compose());
      QueueWriter.write(clientCn.socket, UnseenItemsComposer.compose(clientCn.avatarData.UnseenItems));
    }
    QueueWriter.write(Main.socket, BuyNotificationComposer.compose(product));
  }
}


