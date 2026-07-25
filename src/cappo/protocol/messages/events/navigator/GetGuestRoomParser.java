package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.settings.ControllerLevels;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.GuestRoomResultComposer;

public class GetGuestRoomParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    RoomData room = RoomManager.getRoom(cn.currentPacket.readInt());
    if (room == null) {
      return;
    }
    boolean isLoading = cn.currentPacket.readInt() == 1;
    boolean isPreEnter = cn.currentPacket.readInt() == 1;
    boolean freeToEnter = ControllerLevels.getLevel(cn.playerData, room, room.room) >= 4;
    QueueWriter.write(cn.socket, GuestRoomResultComposer.compose(room, isLoading, isPreEnter, freeToEnter));
  }
}


