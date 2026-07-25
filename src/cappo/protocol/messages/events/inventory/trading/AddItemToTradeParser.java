package cappo.protocol.messages.events.inventory.trading;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.inventory.trading.Trade;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.player.PlayerData;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.item.Item;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.trading.TradingAcceptComposer;
import cappo.protocol.messages.composers.inventory.trading.TradingItemListComposer;
import java.util.Map;

public class AddItemToTradeParser
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
    if (user.status != 0) {
      return;
    }
    Item item = Main.inventory.getFurni(Main.currentPacket.readInt());
    if (item == null) {
      return;
    }
    if (user.furnis.containsKey(Integer.valueOf(item.itemId))) {
      return;
    }
    user.furnis.put(Integer.valueOf(item.itemId), item);
    
    trade.broadcast(TradingItemListComposer.compose(trade.ownerUser, trade.guestUser));
    if (trade.ownerUser.status != 0)
    {
      trade.ownerUser.status = 0;
      trade.broadcast(TradingAcceptComposer.compose(trade.ownerUser.userId, 0));
    }
    if (trade.guestUser.status != 0)
    {
      trade.guestUser.status = 0;
      trade.broadcast(TradingAcceptComposer.compose(trade.guestUser.userId, 0));
    }
  }
}


