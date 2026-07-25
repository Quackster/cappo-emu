package cappo.game.inventory.trading;

import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.composers.inventory.furni.FurniListUpdateComposer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Trade
{
  public static final Map<Integer, Trade> tradeMap = new ConcurrentHashMap();
  public final TradeUser ownerUser;
  public final TradeUser guestUser;
  
  public Trade(Connection owner, Connection guest)
  {
    this.ownerUser = new TradeUser(owner);
    tradeMap.put(Integer.valueOf(this.ownerUser.userId), this);
    
    this.guestUser = new TradeUser(guest);
    tradeMap.put(Integer.valueOf(this.guestUser.userId), this);
  }
  
  public void clean()
  {
    tradeMap.remove(Integer.valueOf(this.ownerUser.userId));
    tradeMap.remove(Integer.valueOf(this.guestUser.userId));
    if (this.ownerUser.connection.avatar.HaveStatus("trd")) {
      this.ownerUser.connection.avatar.setStatus("", "");
    }
    if (this.guestUser.connection.avatar.HaveStatus("trd")) {
      this.guestUser.connection.avatar.setStatus("", "");
    }
    if (!this.ownerUser.furnis.isEmpty()) {
      this.ownerUser.furnis.clear();
    }
    if (!this.guestUser.furnis.isEmpty()) {
      this.guestUser.furnis.clear();
    }
    broadcast(FurniListUpdateComposer.compose());
  }
  
  public void broadcast(MessageWriter packet)
  {
    QueueWriter.writeAndFlush(this.ownerUser.connection.socket, packet);
    QueueWriter.writeAndFlush(this.guestUser.connection.socket, packet);
  }
}


