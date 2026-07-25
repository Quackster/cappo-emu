package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.roomlisting.RoomListing;
import cappo.protocol.messages.IncomingMessageEvent;
import java.util.Map;

public class PopularRoomsSearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int Category = Integer.parseInt(Main.currentPacket.readString());
    if (Category == -1)
    {
      if (RoomListing.PopularRooms != null) {
        QueueWriter.write(Main.socket, RoomListing.PopularRooms);
      }
    }
    else
    {
      MessageWriter packet = (MessageWriter)RoomListing.ByCatRooms.get(Integer.valueOf(Category));
      if (packet != null) {
        QueueWriter.write(Main.socket, packet);
      }
    }
  }
}


