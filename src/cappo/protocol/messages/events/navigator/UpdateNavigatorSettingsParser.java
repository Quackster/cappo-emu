package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.NavigatorSettingsComposer;

public class UpdateNavigatorSettingsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    RoomData room = RoomManager.getRoom(Main.currentPacket.readInt());
    if (room == null) {
      return;
    }
    Main.homeRoom = room.roomId;
    QueueWriter.write(Main.socket, NavigatorSettingsComposer.compose(room.roomId, 0));
  }
}


