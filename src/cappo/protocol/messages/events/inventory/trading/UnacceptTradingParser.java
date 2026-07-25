package cappo.protocol.messages.events.inventory.trading;

import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.inventory.trading.Trade;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.trading.TradingAcceptComposer;
import java.util.Map;

public class UnacceptTradingParser
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
    if (user.status != 1) {
      return;
    }
    user.status = 0;
    
    MessageWriter Message = TradingAcceptComposer.compose(Main.playerData.userId, 0);
    QueueWriter.writeAndFlush(trade.ownerUser.connection.socket, Message);
    QueueWriter.writeAndFlush(trade.guestUser.connection.socket, Message);
  }
}


