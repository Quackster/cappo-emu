package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWar;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeGame2GameObjects;
import cappo.protocol.messages.composers.serializers.SerializeGameStatus;
import java.util.Map;

public class FullGameStatusComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(SnowWarRoom arena)
  {
    MessageWriter ClientMessage = new MessageWriter(1000 + arena.gameObjects.size() * 100 + arena.gameObjects.size() * 50);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    SerializeGame2GameObjects.parse(ClientMessage, arena);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(SnowWar.TEAMS.length), ClientMessage);
    SerializeGameStatus.parse(ClientMessage, arena, true);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


