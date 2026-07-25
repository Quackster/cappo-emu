package cappo.protocol.messages.events.landing;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.landing.NextLimitedAvailableComposer;

public class GetNextLimitedAvailableParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, NextLimitedAvailableComposer.compose());
  }
}


