package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeGame2GameObjects;
import java.util.Map;

public class StageStartingComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(SnowWarRoom arena)
  {
    MessageWriter ClientMessage = new MessageWriter(1000 + arena.gameObjects.size() * 100);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add("snowwar_arena_0", ClientMessage);
    Composer.add(Integer.valueOf(5), ClientMessage);
    SerializeGame2GameObjects.parse(ClientMessage, arena);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


