package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.RoomData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer;
import java.util.ArrayList;
import java.util.List;

public class LatestEventsSearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    List<RoomData> roomList = new ArrayList();
    int Category = Integer.parseInt(Main.currentPacket.readString());
    QueueWriter.write(Main.socket, GuestRoomSearchResultComposer.compose(Category, "12", roomList));
  }
}


