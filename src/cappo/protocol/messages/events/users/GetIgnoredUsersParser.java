package cappo.protocol.messages.events.users;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.IgnoredUsersComposer;

public class GetIgnoredUsersParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, IgnoredUsersComposer.compose());
  }
}


