package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2SnowWarGameStats
{
  public static void parse(MessageWriter ClientMessage, SnowWarRoom arena)
  {
    Composer.add(Integer.valueOf(arena.MostKills.userId), ClientMessage);
    Composer.add(Integer.valueOf(arena.MostHits.userId), ClientMessage);
  }
}


