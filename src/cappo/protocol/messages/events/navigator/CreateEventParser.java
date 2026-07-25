package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomEvent;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.EventComposer;
import java.util.ArrayList;
import java.util.List;

public class CreateEventParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    RoomTask room = avatar.room;
    if ((room.roomData.event != null) || (room.roomData.state != 0)) {
      return;
    }
    int category = Main.currentPacket.readInt();
    String name = Main.currentPacket.readString();
    String description = Main.currentPacket.readString();
    
    room.roomData.event = new RoomEvent(room.roomId, name, description, category, null, (int)Utils.getTimestamp());
    room.roomData.event.tags = new ArrayList();
    
    int tagCount = Main.currentPacket.readInt();
    for (int i = 0; i < tagCount; i++) {
      room.roomData.event.tags.add(Main.currentPacket.readString());
    }
    room.sendMessage(EventComposer.compose(Main.playerData.userId, Main.playerData.userName, room.roomId, room.roomData.event.category, room.roomData.event.name, room.roomData.event.description, room.roomData.event.startTime));
  }
}


