package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameevents.PlayerLeft;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2EventPlayerLeft
{
  public static void parse(MessageWriter ClientMessage, PlayerLeft evt)
  {
    Composer.add(Integer.valueOf(evt.player.objectId), ClientMessage);
  }
}


