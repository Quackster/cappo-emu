package cappo.protocol.messages.events.inventory.trading;

import cappo.engine.database.Database;
import cappo.engine.player.Connection;
import cappo.game.inventory.trading.Trade;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.Item;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.trading.TradingCompletedComposer;
import java.util.Map;

public class ConfirmAcceptTradingParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    PlayerData playerData = Main.getPlayerData();
    
    Trade trade = (Trade)Trade.tradeMap.get(Integer.valueOf(playerData.userId));
    if (trade == null) {
      return;
    }
    TradeUser user = trade.guestUser;
    if (trade.ownerUser.userId == playerData.userId) {
      user = trade.ownerUser;
    }
    if (user.status != 1) {
      return;
    }
    user.status = 2;
    if (trade.guestUser.status == trade.ownerUser.status)
    {
      if (!trade.ownerUser.furnis.isEmpty())
      {
        PlayerData newPlayerData = trade.guestUser.connection.getPlayerData();
        for (Item furni : trade.ownerUser.furnis.values())
        {
          Database.exec("UPDATE furnis SET userid=" + trade.guestUser.userId + " WHERE id=" + furni.itemId + ";", new Object[0]);
          if ((furni instanceof FloorItem))
          {
            trade.ownerUser.connection.inventoryRemoveItem(furni.itemId, false);
            
            FloorItem floorItem = (FloorItem)furni;
            floorItem.owner = newPlayerData;
            floorItem.setMysqlState(1);
            trade.guestUser.connection.inventoryAddFloorItem(floorItem);
          }
          else
          {
            trade.ownerUser.connection.inventoryRemoveItem(furni.itemId, true);
            
            WallItem wallItem = (WallItem)furni;
            wallItem.owner = newPlayerData;
            wallItem.setMysqlState(1);
            trade.guestUser.connection.inventoryAddWallItem(wallItem);
          }
        }
        trade.ownerUser.furnis.clear();
      }
      if (!trade.guestUser.furnis.isEmpty())
      {
        PlayerData newPlayerData = trade.ownerUser.connection.getPlayerData();
        for (Item furni : trade.guestUser.furnis.values())
        {
          Database.exec("UPDATE furnis SET userid=" + trade.ownerUser.userId + " WHERE id=" + furni.itemId + ";", new Object[0]);
          if ((furni instanceof FloorItem))
          {
            trade.guestUser.connection.inventoryRemoveItem(furni.itemId, false);
            
            FloorItem floorItem = (FloorItem)furni;
            floorItem.owner = newPlayerData;
            floorItem.setMysqlState(1);
            trade.ownerUser.connection.inventoryAddFloorItem(floorItem);
          }
          else
          {
            trade.guestUser.connection.inventoryRemoveItem(furni.itemId, true);
            
            WallItem wallItem = (WallItem)furni;
            wallItem.owner = newPlayerData;
            wallItem.setMysqlState(1);
            trade.ownerUser.connection.inventoryAddWallItem(wallItem);
          }
        }
        trade.guestUser.furnis.clear();
      }
      trade.clean();
      trade.broadcast(TradingCompletedComposer.compose());
    }
  }
}


