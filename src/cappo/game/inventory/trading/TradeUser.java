package cappo.game.inventory.trading;

import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.Item;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TradeUser
{
  public int userId;
  public Connection connection;
  public Map<Integer, Item> furnis;
  public int status;
  
  public TradeUser(Connection user)
  {
    this.userId = user.playerData.userId;
    this.connection = user;
    this.furnis = new ConcurrentHashMap();
  }
}


