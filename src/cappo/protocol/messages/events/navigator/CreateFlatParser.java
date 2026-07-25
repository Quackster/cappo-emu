package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.FlatCreatedComposer;
import java.util.Map;

public class CreateFlatParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (Main.ownRooms.size() >= Main.MaxRooms) {
      return;
    }
    RoomData NewRoom = RoomManager.createRoom(Main, Main.currentPacket.readString(), Main.currentPacket.readString());
    if (NewRoom != null) {
      QueueWriter.write(Main.socket, FlatCreatedComposer.compose(NewRoom.roomId, NewRoom.name));
    }
  }
}


