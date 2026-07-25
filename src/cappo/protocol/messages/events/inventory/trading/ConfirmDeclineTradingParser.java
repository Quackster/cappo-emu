package cappo.protocol.messages.events.inventory.trading;

import cappo.engine.player.Connection;
import cappo.game.inventory.trading.Trade;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.trading.TradingCloseComposer;
import java.util.Map;

public class ConfirmDeclineTradingParser
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
    if ((trade.ownerUser.status == 0) || (trade.guestUser.status == 0)) {
      return;
    }
    trade.clean();
    trade.broadcast(TradingCloseComposer.compose(Main.playerData.userId, 0));
  }
}


