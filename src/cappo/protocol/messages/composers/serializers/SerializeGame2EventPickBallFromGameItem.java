package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameevents.PickBallFromGameItem;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.games.snowwar.gameobjects.PickBallsGameItemObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2EventPickBallFromGameItem
{
  public static void parse(MessageWriter ClientMessage, PickBallFromGameItem evt)
  {
    Composer.add(Integer.valueOf(evt.player.objectId), ClientMessage);
    Composer.add(Integer.valueOf(evt.gameItem.objectId), ClientMessage);
  }
}


