package cappo.protocol.messages.events.navigator;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.CanCreateRoomComposer;
import java.util.Map;

public class CanCreateRoomParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (Main.ownRooms.size() < Main.MaxRooms) {
      QueueWriter.write(Main.socket, CanCreateRoomComposer.compose(0, 0));
    } else {
      QueueWriter.write(Main.socket, CanCreateRoomComposer.compose(1, Main.MaxRooms));
    }
  }
}


