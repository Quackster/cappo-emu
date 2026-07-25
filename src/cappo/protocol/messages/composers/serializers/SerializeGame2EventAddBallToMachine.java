package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameevents.AddBallToMachine;
import cappo.game.games.snowwar.gameobjects.MachineGameObject;
import cappo.protocol.messages.Composer;

public class SerializeGame2EventAddBallToMachine
{
  public static void parse(MessageWriter ClientMessage, AddBallToMachine evt)
  {
    Composer.add(Integer.valueOf(evt.gameItem.objectId), ClientMessage);
  }
}


