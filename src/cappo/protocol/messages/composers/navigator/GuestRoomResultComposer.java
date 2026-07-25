package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.RoomData;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeRoom;
import cappo.protocol.messages.composers.serializers.SerializeRoomChatConfig;
import cappo.protocol.messages.composers.serializers.SerializeRoomModerationPermissions;

public class GuestRoomResultComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(RoomData room, boolean isLoading, boolean isPreEnter, boolean freeToEnter)
  {
    MessageWriter writer = new MessageWriter();
    Composer.initPacket(HEADER, writer);
    Composer.add(Boolean.valueOf(isLoading), writer);
    SerializeRoom.parse(writer, room);
    Composer.add(Boolean.valueOf(isPreEnter), writer);
    Composer.add(Boolean.valueOf(room.haveFlag(32)), writer);
    Composer.add(Boolean.valueOf(freeToEnter), writer);
    Composer.add(Boolean.valueOf(room.muteAllOn), writer);
    SerializeRoomModerationPermissions.parse(room.modPermissions, writer);
    Composer.add(Boolean.valueOf(true), writer);
    SerializeRoomChatConfig.parse(room.chatSettings, writer);
    Composer.endPacket(writer);
    return writer;
  }
}


