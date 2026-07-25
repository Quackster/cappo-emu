package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.collections.FavRoom;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.FavouriteChangedComposer;
import java.util.Map;

public class AddFavouriteRoomParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (Main.favoriteRooms.size() >= 30) {
      return;
    }
    int roomId = Main.currentPacket.readInt();
    if (Main.favoriteRooms.containsKey(Integer.valueOf(roomId))) {
      return;
    }
    RoomData room = RoomManager.getRoom(roomId);
    if (room == null) {
      return;
    }
    QueueWriter.write(Main.socket, FavouriteChangedComposer.compose(roomId, Boolean.valueOf(true)));
    FavRoom fav = new FavRoom(room);
    fav.needInsert = true;
    Main.favoriteRooms.put(Integer.valueOf(roomId), fav);
  }
}


