package cappo.protocol.messages.events.talents;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class GetTalentTrackParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
    throws Exception
  {
    Main.currentPacket.readString();
  }
}


