package cappo.protocol.messages.events.inventory.trading;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.inventory.trading.Trade;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.Item;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.trading.TradingItemListComposer;
import java.util.Map;

public class RemoveItemFromTradeParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    Trade trade = (Trade)Trade.tradeMap.get(Integer.valueOf(Main.playerData.userId));
    if (trade == null) {
      return;
    }
    TradeUser user = trade.guestUser;
    if (trade.ownerUser.userId == Main.playerData.userId) {
      user = trade.ownerUser;
    }
    Item item = (Item)user.furnis.remove(Integer.valueOf(Main.currentPacket.readInt()));
    if (item == null) {
      return;
    }
    trade.broadcast(TradingItemListComposer.compose(trade.ownerUser, trade.guestUser));
  }
}


