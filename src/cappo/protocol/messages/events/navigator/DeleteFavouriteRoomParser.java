package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.FavouriteChangedComposer;
import java.util.Map;

public class DeleteFavouriteRoomParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int roomId = Main.currentPacket.readInt();
    Main.favoriteRooms.remove(Integer.valueOf(roomId));
    QueueWriter.write(Main.socket, FavouriteChangedComposer.compose(roomId, Boolean.valueOf(false)));
  }
}


