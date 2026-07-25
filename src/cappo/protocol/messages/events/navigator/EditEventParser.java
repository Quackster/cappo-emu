package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomEvent;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.EventComposer;
import java.util.List;

public class EditEventParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    RoomData roomData = avatar.room.roomData;
    if (roomData.event == null) {
      return;
    }
    roomData.event.category = Main.currentPacket.readInt();
    roomData.event.name = Main.currentPacket.readString();
    roomData.event.description = Main.currentPacket.readString();
    roomData.event.tags.clear();
    
    int tagCount = Main.currentPacket.readInt();
    for (int i = 0; i < tagCount; i++) {
      roomData.event.tags.add(Main.currentPacket.readString());
    }
    avatar.room.sendMessage(EventComposer.compose(Main.playerData.userId, Main.playerData.userName, roomData.roomId, roomData.event.category, roomData.event.name, roomData.event.description, roomData.event.startTime));
  }
}


