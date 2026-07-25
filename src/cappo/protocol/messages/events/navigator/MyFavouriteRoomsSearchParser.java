package cappo.protocol.messages.events.navigator;

import cappo.engine.logging.Log;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.collections.FavRoom;
import cappo.game.roomengine.RoomData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyFavouriteRoomsSearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    List<RoomData> roomList = new ArrayList();
    for (FavRoom fav : Main.favoriteRooms.values()) {
      try
      {
        roomList.add(fav.room);
      }
      catch (Exception ex)
      {
        Log.printException("MyFavouriteRoomsSearchParser-1", ex);
      }
    }
    QueueWriter.write(Main.socket, GuestRoomSearchResultComposer.compose(0, "6", roomList));
  }
}


