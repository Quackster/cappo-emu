package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.player.Connection;
import cappo.game.games.snowwar.SnowPlayerQueue;
import cappo.protocol.messages.IncomingMessageEvent;

public class QuickJoinGameParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    SnowPlayerQueue.addPlayerInQueue(Main);
  }
}


