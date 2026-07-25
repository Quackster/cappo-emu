package cappo.protocol.messages.events.navigator;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.roomlisting.RoomListing;
import cappo.protocol.messages.IncomingMessageEvent;

public class RoomsWithHighestScoreSearchParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (RoomListing.ScoreRooms != null) {
      QueueWriter.write(Main.socket, RoomListing.ScoreRooms);
    }
  }
}


