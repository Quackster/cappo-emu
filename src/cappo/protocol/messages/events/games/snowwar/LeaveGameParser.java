package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.player.Connection;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.IncomingMessageEvent;

public class LeaveGameParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.snowWarPlayerData.userLeft();
  }
}


