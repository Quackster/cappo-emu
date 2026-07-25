package cappo.protocol.messages.events.inventory.trading;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.inventory.trading.Trade;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.trading.TradingAlreadyOpenComposer;
import cappo.protocol.messages.composers.inventory.trading.TradingOpenComposer;
import java.util.Map;

public class OpenTradingParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if (Trade.tradeMap.containsKey(Integer.valueOf(Main.playerData.userId)))
    {
      QueueWriter.write(Main.socket, TradingAlreadyOpenComposer.compose(7, ""));
      return;
    }
    Avatar User = avatar.room.getRoomUserByVirtualId(Main.currentPacket.readInt());
    if (User == null) {
      return;
    }
    if (Trade.tradeMap.containsKey(Integer.valueOf(User.cn.playerData.userId)))
    {
      QueueWriter.write(Main.socket, TradingAlreadyOpenComposer.compose(8, User.cn.playerData.userName));
      return;
    }
    if (User.cn.haveFlag(8))
    {
      QueueWriter.write(Main.socket, TradingAlreadyOpenComposer.compose(2, User.cn.playerData.userName));
      return;
    }
    avatar.setStatus("trd", "");
    User.setStatus("trd", "");
    
    Trade trade = new Trade(Main, User.cn);
    trade.broadcast(TradingOpenComposer.compose(trade.ownerUser.userId, trade.guestUser.userId));
  }
}


