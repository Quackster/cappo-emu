package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameevents.UserMove;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2EventMove
{
  public static void parse(MessageWriter ClientMessage, UserMove evt)
  {
    Composer.add(Integer.valueOf(evt.player.objectId), ClientMessage);
    Composer.add(Integer.valueOf(evt.x), ClientMessage);
    Composer.add(Integer.valueOf(evt.y), ClientMessage);
  }
}


