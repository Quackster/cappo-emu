package cappo.protocol.messages.events.navigator;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.RoomData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer;
import java.util.ArrayList;
import java.util.List;

public class MyRoomHistorySearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    List<RoomData> roomList = new ArrayList();
    QueueWriter.write(Main.socket, GuestRoomSearchResultComposer.compose(0, "7", roomList));
  }
}


