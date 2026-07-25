package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.Composer;

public class SerializeGame2GameResult
{
  public static void parse(MessageWriter ClientMessage, SnowWarRoom arena)
  {
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Integer.valueOf(arena.Result), ClientMessage);
    Composer.add(Integer.valueOf(arena.Winner), ClientMessage);
  }
}


