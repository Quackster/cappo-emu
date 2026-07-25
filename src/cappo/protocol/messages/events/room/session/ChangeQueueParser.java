package cappo.protocol.messages.events.room.session;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class ChangeQueueParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int param = Main.currentPacket.readInt();
  }
}


