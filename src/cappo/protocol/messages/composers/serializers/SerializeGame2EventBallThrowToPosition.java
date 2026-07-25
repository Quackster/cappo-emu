package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameevents.BallThrowToPosition;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2EventBallThrowToPosition
{
  public static void parse(MessageWriter ClientMessage, BallThrowToPosition evt)
  {
    Composer.add(Integer.valueOf(evt.attacker.objectId), ClientMessage);
    Composer.add(Integer.valueOf(evt.x), ClientMessage);
    Composer.add(Integer.valueOf(evt.y), ClientMessage);
    Composer.add(Integer.valueOf(evt.type), ClientMessage);
  }
}


