package cappo.game.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomQueue
{
  public SnowWarRoom room;
  public final Map<Integer, Connection> players = new ConcurrentHashMap(10);
  
  public RoomQueue(SnowWarRoom snowRoom)
  {
    this.room = snowRoom;
  }
  
  public void broadcast(MessageWriter Message)
  {
    for (Connection cn : this.players.values()) {
      QueueWriter.writeAndFlush(cn.socket, Message);
    }
  }
}


