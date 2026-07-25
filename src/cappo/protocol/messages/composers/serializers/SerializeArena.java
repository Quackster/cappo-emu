package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.GamefuseObject;
import cappo.game.games.snowwar.SnowWarArenaBase;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.Composer;
import java.util.List;

public class SerializeArena
{
  public static void parse(MessageWriter ClientMessage, SnowWarRoom arena)
  {
    Composer.add(Integer.valueOf(arena.ArenaType.ArenaWidth), ClientMessage);
    Composer.add(Integer.valueOf(arena.ArenaType.ArenaHeight), ClientMessage);
    Composer.add(arena.ArenaType.HeightMap, ClientMessage);
    Composer.add(Integer.valueOf(arena.ArenaType.fuseObjects.size()), ClientMessage);
    for (GamefuseObject fuseItem : arena.ArenaType.fuseObjects) {
      SerializeFuseObject.parse(ClientMessage, fuseItem);
    }
  }
}


