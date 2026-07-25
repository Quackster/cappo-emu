package cappo.protocol.messages.events.handshake;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class VersionCheckParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.currentPacket.readInt();
    Main.currentPacket.readString();
    Main.currentPacket.readString();
  }
}


