package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class SerializeGame2TeamScoreData
{
  public static void parse(MessageWriter ClientMessage, int TeamId, int TeamScore, Collection<HumanGameObject> Players)
  {
    Composer.add(Integer.valueOf(TeamId), ClientMessage);
    Composer.add(Integer.valueOf(TeamScore), ClientMessage);
    Composer.add(Integer.valueOf(Players.size()), ClientMessage);
    for (HumanGameObject Player : Players) {
      SerializeGame2TeamPlayerData.parse(ClientMessage, Player);
    }
  }
}


