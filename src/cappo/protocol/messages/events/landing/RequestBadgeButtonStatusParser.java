package cappo.protocol.messages.events.landing;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.landing.BadgeButtonStatusComposer;

public class RequestBadgeButtonStatusParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    boolean hidde = false;
    QueueWriter.write(Main.socket, BadgeButtonStatusComposer.compose(Main.currentPacket.readString(), false));
  }
}


