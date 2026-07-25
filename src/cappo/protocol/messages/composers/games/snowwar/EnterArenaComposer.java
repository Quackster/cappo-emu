package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWar;
import cappo.game.games.snowwar.SnowWarArenaBase;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeArena;
import cappo.protocol.messages.composers.serializers.SerializeGame2PlayerData;
import java.util.List;
import java.util.Map;

public class EnterArenaComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(SnowWarRoom arena)
  {
    MessageWriter ClientMessage = new MessageWriter(5000 + arena.ArenaType.fuseObjects.size() * 100);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(arena.ArenaType.ArenaType), ClientMessage);
    Composer.add(Integer.valueOf(SnowWar.TEAMS.length), ClientMessage);
    Composer.add(Integer.valueOf(arena.players.size()), ClientMessage);
    for (HumanGameObject Player : arena.players.values()) {
      SerializeGame2PlayerData.parse(ClientMessage, Player);
    }
    SerializeArena.parse(ClientMessage, arena);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


