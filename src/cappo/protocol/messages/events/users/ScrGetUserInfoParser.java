package cappo.protocol.messages.events.users;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.ScrUserInfoComposer;

public class ScrGetUserInfoParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, ScrUserInfoComposer.compose(Main, 1));
  }
}


