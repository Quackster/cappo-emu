package cappo.protocol.messages.events.games.gamecenter;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.games.gamecenter.GameAchievementsComposer;

public class GetGameAchievementsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, GameAchievementsComposer.compose());
  }
}


