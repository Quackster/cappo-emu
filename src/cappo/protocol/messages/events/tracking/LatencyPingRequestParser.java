package cappo.protocol.messages.events.tracking;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.tracking.PingResponseComposer;

public class LatencyPingRequestParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, PingResponseComposer.compose(Main.currentPacket.readInt()));
  }
}


