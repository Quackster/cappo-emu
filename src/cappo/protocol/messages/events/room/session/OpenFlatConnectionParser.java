package cappo.protocol.messages.events.room.session;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class OpenFlatConnectionParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    cn.loadRoom(cn.currentPacket.readInt(), cn.currentPacket.readString());
  }
}


