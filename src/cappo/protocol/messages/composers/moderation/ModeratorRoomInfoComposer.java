package cappo.protocol.messages.composers.moderation;

import cappo.engine.network.MessageWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.Composer;

public class ModeratorRoomInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int RoomId)
  {
    RoomData room = RoomManager.getRoom(RoomId);
    if (room == null) {
      return null;
    }
    boolean OwnerInRoom = false;
    if ((room.roomOwner != null) && (room.roomOwner.connection != null)) {
      OwnerInRoom = room.roomOwner.connection.avatar.room.roomData == room;
    }
    MessageWriter clientMessage = new MessageWriter();
    Composer.initPacket(HEADER, clientMessage);
    Composer.add(Integer.valueOf(room.roomId), clientMessage);
    Composer.add(Integer.valueOf(room.room != null ? room.room.userCount : 0), clientMessage);
    Composer.add(Boolean.valueOf(OwnerInRoom), clientMessage);
    Composer.add(Integer.valueOf(room.roomOwnerId), clientMessage);
    Composer.add(room.roomOwnerName, clientMessage);
    Composer.add(Boolean.valueOf(true), clientMessage);
    Composer.add(room.name, clientMessage);
    Composer.add(room.description, clientMessage);
    Composer.add(Integer.valueOf(room.tags.length), clientMessage);
    for (String Tag : room.tags) {
      Composer.add(Tag, clientMessage);
    }
    Composer.endPacket(clientMessage);
    return clientMessage;
  }
}


