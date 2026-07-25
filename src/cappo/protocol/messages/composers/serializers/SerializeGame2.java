package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.engine.player.Connection;
import cappo.game.games.snowwar.RoomQueue;
import cappo.game.games.snowwar.SnowWar;
import cappo.game.games.snowwar.SnowWarArenaBase;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.Composer;
import java.util.Map;

public class SerializeGame2
{
  public static void parse(MessageWriter ClientMessage, RoomQueue queue)
  {
    Composer.add(Integer.valueOf(queue.room.roomId), ClientMessage);
    Composer.add(queue.room.Name, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(queue.room.ArenaType.ArenaType), ClientMessage);
    Composer.add(Integer.valueOf(SnowWar.TEAMS.length), ClientMessage);
    Composer.add(Integer.valueOf(10), ClientMessage);
    Composer.add(queue.room.Owner, ClientMessage);
    Composer.add(Integer.valueOf(14), ClientMessage);
    Composer.add(Integer.valueOf(queue.players.size()), ClientMessage);
    for (Connection cn : queue.players.values()) {
      SerializeGame2Player.parse(ClientMessage, cn);
    }
  }
}


