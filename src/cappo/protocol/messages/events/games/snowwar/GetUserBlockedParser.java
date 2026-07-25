package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.games.snowwar.UserBlockedComposer;

public class GetUserBlockedParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, UserBlockedComposer.compose(0));
  }
}


