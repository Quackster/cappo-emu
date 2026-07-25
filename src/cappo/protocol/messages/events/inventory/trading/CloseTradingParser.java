package cappo.protocol.messages.events.inventory.trading;

import cappo.engine.player.Connection;
import cappo.game.inventory.trading.Trade;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.trading.TradingCloseComposer;
import java.util.Map;

public class CloseTradingParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Trade trade = (Trade)Trade.tradeMap.get(Integer.valueOf(Main.playerData.userId));
    if (trade == null) {
      return;
    }
    trade.clean();
    trade.broadcast(TradingCloseComposer.compose(Main.playerData.userId, 0));
  }
}


