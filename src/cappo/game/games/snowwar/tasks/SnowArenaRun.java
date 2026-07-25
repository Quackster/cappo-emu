package cappo.game.games.snowwar.tasks;

import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.GameItemObject;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.composers.games.snowwar.FullGameStatusComposer;
import cappo.protocol.messages.composers.games.snowwar.GameStatusComposer;
import cappo.protocol.messages.composers.games.snowwar.StageEndingComposer;
import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SnowArenaRun
{
  public static void exec(SnowWarRoom room)
  {
    if (room.players.isEmpty())
    {
      room.STATUS = 0; return;
    }
    Channel socket;
    List<Channel> filter;
    MessageWriter status;
    synchronized (room.gameEvents)
    {
      synchronized (room.fullGameStatusQueue)
      {
        filter = room.fullGameStatusQueue;
        room.fullGameStatusQueue = new ArrayList();
      }
      room.checksum = 0;
      for (GameItemObject Object : room.gameObjects.values()) {
        Object.GenerateCHECKSUM(room, 1);
      }
      for (Iterator<Channel> localIterator = filter.iterator(); localIterator.hasNext();)
      {
        socket = (Channel)localIterator.next();
        QueueWriter.writeAndFlush(socket, FullGameStatusComposer.compose(room));
      }
      status = GameStatusComposer.compose(room);
      room.gameEvents.clear();
    }
    for (HumanGameObject player : room.players.values()) {
      if ((player.currentSnowWar != null) && (
        (filter == null) || (filter.isEmpty()) || 
        (!filter.contains(player.cn.socket)))) {
        QueueWriter.writeAndFlush(player.cn.socket, status);
      }
    }
    room.subturn();
    room.subturn();
    room.subturn();
    if (++room.Turn >= 800)
    {
      room.STATUS = 6;
      room.broadcast(StageEndingComposer.compose());
    }
  }
}
