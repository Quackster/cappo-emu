package cappo.protocol.messages.events.tracking;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class LatencyPingReportParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.currentPacket.readInt();
    Main.currentPacket.readInt();
    Main.currentPacket.readInt();
  }
}


