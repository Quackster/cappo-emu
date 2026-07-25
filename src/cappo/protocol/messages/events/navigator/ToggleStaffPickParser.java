package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.RoomUpdatedComposer;

public class ToggleStaffPickParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    RoomData roomData = RoomManager.getRoom(Main.currentPacket.readInt());
    if (roomData == null) {
      return;
    }
    roomData.xorFlag(32);
    
    RoomTask room = roomData.room;
    if (room != null) {
      room.sendMessage(RoomUpdatedComposer.compose(room.roomId));
    }
  }
}


