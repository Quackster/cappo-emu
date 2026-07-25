package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeRoom;
import java.util.Collection;

public class GuestRoomSearchResultComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int searchType, String Type, Collection<RoomData> roomList)
  {
    MessageWriter ClientMessage = new MessageWriter(100 + roomList.size() * 800);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(searchType), ClientMessage);
    Composer.add(Type, ClientMessage);
    Composer.add(Integer.valueOf(roomList.size()), ClientMessage);
    for (RoomData room : roomList) {
      SerializeRoom.parse(ClientMessage, room);
    }
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
  
  public static final MessageWriter compose2(int searchType, String Type, Collection<RoomTask> roomList)
  {
    MessageWriter ClientMessage = new MessageWriter(100 + roomList.size() * 800);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(searchType), ClientMessage);
    Composer.add(Type, ClientMessage);
    Composer.add(Integer.valueOf(roomList.size()), ClientMessage);
    for (RoomTask room : roomList) {
      SerializeRoom.parse(ClientMessage, room.roomData);
    }
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


