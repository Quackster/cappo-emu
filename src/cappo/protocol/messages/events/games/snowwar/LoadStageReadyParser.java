package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.player.Connection;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.IncomingMessageEvent;

public class LoadStageReadyParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    SnowWarPlayerData snowPlayer = Main.snowWarPlayerData;
    if (snowPlayer.currentSnowWar == null) {
      return;
    }
    HumanGameObject humanObject = snowPlayer.humanObject;
    if (humanObject == null) {
      return;
    }
    snowPlayer.currentSnowWar.stageLoaded(humanObject);
  }
}


