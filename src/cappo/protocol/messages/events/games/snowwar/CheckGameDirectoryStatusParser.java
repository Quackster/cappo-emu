package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.games.snowwar.GameDirectoryStatusComposer;

public class CheckGameDirectoryStatusParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, GameDirectoryStatusComposer.compose(Main.playerData, 0));
  }
}


