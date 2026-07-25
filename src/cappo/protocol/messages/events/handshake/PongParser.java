package cappo.protocol.messages.events.handshake;

import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class PongParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.setFlag(2, true);
  }
}


