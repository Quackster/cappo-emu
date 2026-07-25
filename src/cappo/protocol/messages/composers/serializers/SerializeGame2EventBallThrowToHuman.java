package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameevents.BallThrowToHuman;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2EventBallThrowToHuman
{
  public static void parse(MessageWriter ClientMessage, BallThrowToHuman evt)
  {
    Composer.add(Integer.valueOf(evt.attacker.objectId), ClientMessage);
    Composer.add(Integer.valueOf(evt.victim.objectId), ClientMessage);
    Composer.add(Integer.valueOf(evt.type), ClientMessage);
  }
}


