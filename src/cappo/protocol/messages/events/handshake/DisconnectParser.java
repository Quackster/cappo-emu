package cappo.protocol.messages.events.handshake;

import cappo.engine.network.CappoServer;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class DisconnectParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    CappoServer.close(Main.socket);
  }
}


