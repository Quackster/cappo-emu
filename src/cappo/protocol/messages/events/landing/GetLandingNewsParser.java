package cappo.protocol.messages.events.landing;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.landing.LandingNewsComposer;

public class GetLandingNewsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, LandingNewsComposer.compose());
  }
}


