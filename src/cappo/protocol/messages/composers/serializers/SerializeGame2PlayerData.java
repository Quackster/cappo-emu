package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2PlayerData
{
  public static void parse(MessageWriter ClientMessage, HumanGameObject Player)
  {
    Composer.add(Integer.valueOf(Player.userId), ClientMessage);
    Composer.add(Player.userName, ClientMessage);
    Composer.add(Player.look, ClientMessage);
    Composer.add(Player.sex == 1 ? "M" : "F", ClientMessage);
    Composer.add(Integer.valueOf(Player.team), ClientMessage);
  }
}


