package cappo.protocol.messages.events.room.session;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class GoToFlatParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    RoomData roomData = RoomManager.getRoom(cn.currentPacket.readInt());
    if (roomData == null) {
      return;
    }
    RoomTask room = roomData.room;
    if (room == null) {
      return;
    }
    Avatar avatar = cn.avatar;
    if (avatar != null)
    {
      RoomTask oldRoom = avatar.room;
      if (oldRoom != null) {
        oldRoom.removeUserFromRoom(cn, false, false);
      }
    }
    room.startLoadingRoom(cn);
  }
}


