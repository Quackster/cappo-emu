package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.GameItemObject;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;
import java.util.Map;

public class SerializeGame2GameObjects
{
  public static void parse(MessageWriter ClientMessage, SnowWarRoom arena)
  {
    Composer.add(Integer.valueOf(arena.gameObjects.size()), ClientMessage);
    for (GameItemObject Object : arena.gameObjects.values())
    {
      for (int i = 0; i < Object.variablesCount; i++) {
        Composer.add(Integer.valueOf(Object.getVariable(i)), ClientMessage);
      }
      if (Object.getVariable(0) == 5)
      {
        HumanGameObject Player = (HumanGameObject)Object;
        Composer.add(Player.userName, ClientMessage);
        Composer.add(Player.motto, ClientMessage);
        Composer.add(Player.look, ClientMessage);
        Composer.add(Player.sex == 1 ? "M" : "F", ClientMessage);
      }
    }
  }
}


