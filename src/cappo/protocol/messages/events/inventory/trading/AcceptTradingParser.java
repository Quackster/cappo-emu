package cappo.protocol.messages.events.inventory.trading;

import cappo.engine.player.Connection;
import cappo.game.inventory.trading.Trade;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.trading.TradingAcceptComposer;
import cappo.protocol.messages.composers.inventory.trading.TradingConfirmationComposer;
import java.util.Map;

public class AcceptTradingParser
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
    user.status = 1;
    
    trade.broadcast(TradingAcceptComposer.compose(playerData.userId, user.status));
    if (trade.guestUser.status == trade.ownerUser.status) {
      trade.broadcast(TradingConfirmationComposer.compose());
    }
  }
}


