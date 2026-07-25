package cappo.protocol.messages.events.catalog;

import cappo.engine.Server;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.bots.RentalBot;
import cappo.game.catalog.Catalog;
import cappo.game.catalog.Catalog.CatalogPage;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.game.catalog.Catalog.CatalogSubItem;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BaseItem.FurniLogic;
import cappo.game.collections.Teleports;
import cappo.game.collections.UnseenItems;
import cappo.game.collections.Utils;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.item.extradata.StuffDataReader;
import cappo.game.roomengine.entity.item.extradata.StuffDataWriter;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.catalog.ErrorBuyComposer;
import cappo.protocol.messages.composers.catalog.ErrorPurchaseFromCatalogComposer;
import cappo.protocol.messages.composers.catalog.UniqueLimitedItemSoldOutComposer;
import cappo.protocol.messages.composers.inventory.bots.AddBotToInventoryComposer;
import cappo.protocol.messages.composers.inventory.furni.FurniListUpdateComposer;
import cappo.protocol.messages.composers.inventory.pets.AddPetToInventoryComposer;
import cappo.protocol.messages.composers.inventory.purse.CreditBalanceComposer;
import cappo.protocol.messages.composers.notifications.BuyNotificationComposer;
import cappo.protocol.messages.composers.notifications.HabboActivityPointNotificationComposer;
import cappo.protocol.messages.composers.notifications.PetReceivedMessageComposer;
import cappo.protocol.messages.composers.notifications.UnseenItemsComposer;
import java.text.SimpleDateFormat;
import java.util.Map;

public class PurchaseFromCatalogParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int pageId = Main.currentPacket.readInt();
    int itemId = Main.currentPacket.readInt();
    String extraParam = Main.currentPacket.readString();
    int quantity = Main.currentPacket.readInt();
    if ((quantity > 50) || (quantity < 1)) {
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
    if ((quantity > 1) && ((!product.allowBundleDiscounts) || (product.uniqueLimitedItemsLaunched.intValue() > 0))) {
      return;
    }
    int priceMultipler;
    if (quantity > 5) {
      priceMultipler = quantity - (quantity / 5 * 2 - 1);
    } else {
      priceMultipler = quantity;
    }
    if (product.creditCost > 0)
    {
      int finalCost = product.creditCost * priceMultipler;
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
      int finalCost = product.activityPointCost * priceMultipler;
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
    if ((product.itemName.startsWith("a0 pet")) && (product.itemName.length() <= 8))
    {
      try
      {
        int type = Integer.parseInt(product.itemName.substring(6));
        if ((type < 0) || (type > 27))
        {
          Log.printLog("Invalid Pet:" + type);
          return;
        }
        String[] strArray = extraParam.split("\n");
        
        short raceid = Short.parseShort(strArray[1]);
        if (!Pet.PETS[type].races.containsKey(Short.valueOf(raceid)))
        {
          Log.printLog("Invalid Pet Race: type=" + type + " race=" + raceid);
          return;
        }
        if (!validPetName(strArray[0])) {
          return;
        }
        if (strArray[2].length() != 6) {
          return;
        }
        QueueWriter.write(Main.socket, BuyNotificationComposer.compose(product));
        buyPet(product, strArray, raceid, Main);
      }
      catch (Exception ex)
      {
        Log.printException("PurchaseFromCatalogParser-0", ex);
      }
    }
    else
    {
      if (Main.inventory.isFull(1))
      {
        Utils.AlertFromHotel(Main.socket, cappo.game.utils.lang.LangTexts.texts[6]);
        return;
      }
      if (product.uniqueLimitedItemsLaunched.intValue() > 0)
      {
        if (product.uniqueLimitedItemsLeft.intValue() < 1)
        {
          QueueWriter.write(Main.socket, UniqueLimitedItemSoldOutComposer.compose()); return;
        }
        Catalog.CatalogProduct tmp689_687 = product;tmp689_687.uniqueLimitedItemsLeft = Integer.valueOf(tmp689_687.uniqueLimitedItemsLeft.intValue() - 1);
      }
      QueueWriter.write(Main.socket, BuyNotificationComposer.compose(product));
      buyProduct(product, extraParam, quantity, Main);
    }
    QueueWriter.write(Main.socket, UnseenItemsComposer.compose(Main.avatarData.UnseenItems));
  }
  
  public static void buyProduct(Catalog.CatalogProduct product, String extraParam, int buyAmmount, Connection Main)
  {
    PlayerData playerData = Main.getPlayerData();
    for (Catalog.CatalogSubItem subItem : product.items)
    {
      if (subItem.baseItem.Type.equals("r"))
      {
        for (int j = 0; j < subItem.amount.intValue(); j++) {
          buyBot(product, Main);
        }
        return;
      }
      if (subItem.baseItem.Type.equals("e"))
      {
        for (int j = 0; j < subItem.amount.intValue(); j++)
        {
          int Time = 10;
          Main.addEffect(subItem.baseItem.SpriteId, 601);
        }
        return;
      }
      StuffDataWriter data = null;
      if (subItem.baseItem.itemExtraType == 0)
      {
        data = new StuffDataWriter(0);
        if (subItem.baseItem.logic == BaseItem.FurniLogic.ROOMDIMMER) {
          data.writeString("1,1,1,#000000,255");
        } else if (subItem.baseItem.itemCategory == 11) {
          data.writeString(playerData.userName + '\t' + Server.date.format(Utils.GetDateNow()) + '\t' + extraParam);
        } else {
          data.writeString(extraParam);
        }
      }
      else if (subItem.baseItem.itemExtraType == 1)
      {
        data = new StuffDataWriter(1);
        if (subItem.baseItem.itemCategory == 2)
        {
          data.writeInt8(1);
          data.writeString("state");
          data.writeString(extraParam);
        }
        else if (subItem.baseItem.logic == BaseItem.FurniLogic.MANNEQUIN)
        {
          data.writeInt8(3);
          data.writeString("GENDER");
          data.writeString("M");
          data.writeString("FIGURE");
          data.writeString("lg-270-82.ch-210-66");
          data.writeString("OUTFIT_NAME");
          data.writeString("");
        }
        else
        {
          data.writeInt8(0);
        }
      }
      else if (subItem.baseItem.itemExtraType == 2)
      {
        data = new StuffDataWriter(2);
        if (subItem.baseItem.logic == BaseItem.FurniLogic.BADGEDISPLAY)
        {
          data.writeInt8(4);
          data.writeString("0");
          data.writeString(extraParam);
          data.writeString(playerData.userName);
          data.writeString(Server.date.format(Utils.GetDateNow()));
        }
        else
        {
          String[] parts = extraParam.split(";");
          data.writeInt8(parts.length);
          for (String val : parts) {
            data.writeString(val);
          }
        }
      }
      else if (subItem.baseItem.itemExtraType == 3)
      {
        data = new StuffDataWriter(3);
        data.writeString(extraParam);
        data.writeInt16(0);
      }
      else if (subItem.baseItem.itemExtraType != 4)
      {
        if (subItem.baseItem.itemExtraType == 5)
        {
          data = new StuffDataWriter(5);
          
          String[] parts = extraParam.split(";");
          data.writeInt8(parts.length);
          for (String val : parts) {
            data.writeInt32(Integer.parseInt(val));
          }
        }
        else if (subItem.baseItem.itemExtraType != 6)
        {
          if (subItem.baseItem.itemExtraType == 7)
          {
            data = new StuffDataWriter(7);
            data.writeString(extraParam);
            data.writeInt16(0);
            data.writeInt16(subItem.baseItem.interactionCount);
          }
        }
      }
      if (data == null)
      {
        Log.printLog("Not implemented extraType = " + subItem.baseItem.itemExtraType);
        return;
      }
      if (subItem.baseItem.interactorType == Interactor.InteractorType.teleport)
      {
        for (int x = 0; x < buyAmmount; x++)
        {
          int ref1 = Server.generateRefItemId();
          int ref2 = Server.generateRefItemId();
          
          int Id1 = Server.generateItemId();
          int Id2 = Server.generateItemId();
          
          FloorItem floorItem1 = FloorItem.createItem(playerData, ref1, Id1, new StuffDataReader(data.getData()), subItem.extraParam, subItem.baseItem);
          Main.inventoryAddFloorItem(floorItem1);
          floorItem1.setMysqlState(3);
          FloorItem floorItem2 = FloorItem.createItem(playerData, ref2, Id2, new StuffDataReader(data.getData()), subItem.extraParam, subItem.baseItem);
          Main.inventoryAddFloorItem(floorItem2);
          floorItem2.setMysqlState(3);
          Teleports.setParents(Id1, Id2);
          
          Main.avatarData.UnseenItems.AddItem(1, ref1);
          Main.avatarData.UnseenItems.AddItem(1, ref2);
          try
          {
            Database.exec("INSERT IGNORE INTO items_tele_links (tele_one_id,tele_two_id)VALUES(" + Id1 + "," + Id2 + ");", new Object[0]);
          }
          catch (Exception ex)
          {
            Log.printException("", ex);
          }
        }
      }
      else
      {
        if (subItem.baseItem.interactor == Interactor.iterWired) {
          Main.giveBadge("WIRD2");
        }
        if (subItem.baseItem.Type.equals("s"))
        {
          Main.avatarData.UnseenItems.AddItem(1, subItem.baseItem.SpriteId);
          for (int x = 0; x < buyAmmount; x++) {
            for (int i = 0; i < subItem.amount.intValue(); i++)
            {
              int refId = subItem.baseItem.SpriteId;
              
              FloorItem floorItem = FloorItem.createItem(playerData, refId, Server.generateItemId(), new StuffDataReader(data.getData()), subItem.extraParam, subItem.baseItem);
              Main.inventoryAddFloorItem(floorItem);
              floorItem.setMysqlState(3);
            }
          }
        }
        else if (subItem.baseItem.Type.equals("i"))
        {
          Main.avatarData.UnseenItems.AddItem(1, subItem.baseItem.SpriteId);
          for (int x = 0; x < buyAmmount; x++) {
            for (int i = 0; i < subItem.amount.intValue(); i++)
            {
              int refId = subItem.baseItem.SpriteId;
              
              WallItem wallItem = WallItem.createItem(playerData, refId, Server.generateItemId(), new StuffDataReader(data.getData()), subItem.baseItem);
              Main.inventoryAddWallItem(wallItem);
              wallItem.setMysqlState(3);
            }
          }
        }
      }
    }
    QueueWriter.write(Main.socket, FurniListUpdateComposer.compose());
  }
  
  private static void buyPet(Catalog.CatalogProduct item, String[] strArray, int raceid, Connection Main)
  {
    int PetId = Server.generateItemId();
    
    PlayerData playerData = Main.getPlayerData();
    

    Pet pet = new Pet(PetId, strArray[0], (short)Integer.parseInt(item.itemName.substring(6)), (short)raceid, strArray[2]);
    pet.needInsert = true;
    
    pet.ownerId = playerData.userId;
    pet.ownerName = playerData.userName;
    pet.TimeCreated = Utils.getTimestamp();
    pet.Nutrition = 50;
    pet.Experience = 0;
    pet.Energy = 100;
    pet.Respects = 0;
    pet.level = 1;
    
    Main.inventory.addPet(PetId, pet);
    Main.avatarData.UnseenItems.AddItem(3, PetId);
    QueueWriter.write(Main.socket, AddPetToInventoryComposer.compose(pet));
    QueueWriter.write(Main.socket, PetReceivedMessageComposer.compose(false, pet));
  }
  
  private static void buyBot(Catalog.CatalogProduct item, Connection Main)
  {
    PlayerData playerData = Main.getPlayerData();
    
    int botId = Server.generateItemId();
    short botType = (short)(item.itemName.equals("bot_bartender") ? 1 : 0);
    
    RentalBot bot = new RentalBot(botId, item.itemName, botType);
    bot.setDefaults();
    
    bot.ownerId = playerData.userId;
    bot.ownerName = playerData.userName;
    
    Main.inventory.addBot(botId, bot);
    Main.avatarData.UnseenItems.AddItem(5, botId);
    QueueWriter.write(Main.socket, AddBotToInventoryComposer.compose(bot));
  }
  
  private static boolean validPetName(String inputStr)
  {
    int len = inputStr.length();
    if ((len < 3) || (len > 15)) {
      return false;
    }
    for (char c : inputStr.toCharArray()) {
      if (((c < 'a') || (c > 'z')) && ((c < '0') || (c > '9'))) {
        return false;
      }
    }
    return true;
  }
}


