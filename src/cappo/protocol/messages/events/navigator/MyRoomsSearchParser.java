package cappo.protocol.messages.events.navigator;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer;
import java.util.Map;

public class MyRoomsSearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    QueueWriter.write(cn.socket, GuestRoomSearchResultComposer.compose(0, "5", cn.ownRooms.values()));
  }
}


