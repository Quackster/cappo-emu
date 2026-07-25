package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameevents.CreateSnowBall;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.games.snowwar.gameobjects.SnowBallGameObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2EventCreateSnowBall
{
  public static void parse(MessageWriter ClientMessage, CreateSnowBall evt)
  {
    Composer.add(Integer.valueOf(evt.ball.objectId), ClientMessage);
    Composer.add(Integer.valueOf(evt.player.objectId), ClientMessage);
    Composer.add(Integer.valueOf(evt.x), ClientMessage);
    Composer.add(Integer.valueOf(evt.y), ClientMessage);
    Composer.add(Integer.valueOf(evt.type), ClientMessage);
  }
}


