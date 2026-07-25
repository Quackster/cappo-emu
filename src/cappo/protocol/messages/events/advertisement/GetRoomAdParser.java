package cappo.protocol.messages.events.advertisement;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.advertisement.RoomAdComposer;

public class GetRoomAdParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, RoomAdComposer.compose("", ""));
  }
}


