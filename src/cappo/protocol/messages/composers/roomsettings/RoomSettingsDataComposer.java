package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.settings.TradingSettings;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeRoomChatConfig;
import cappo.protocol.messages.composers.serializers.SerializeRoomModerationPermissions;

public class RoomSettingsDataComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(RoomData room)
  {
    MessageWriter writer = new MessageWriter();
    Composer.initPacket(HEADER, writer);
    Composer.add(Integer.valueOf(room.roomId), writer);
    Composer.add(room.name, writer);
    Composer.add(room.description, writer);
    Composer.add(Integer.valueOf(room.state), writer);
    Composer.add(Integer.valueOf(room.category), writer);
    Composer.add(Integer.valueOf(room.usersMax), writer);
    Composer.add(Integer.valueOf(100), writer);
    Composer.add(Integer.valueOf(room.tags.length), writer);
    for (String tag : room.tags) {
      Composer.add(tag, writer);
    }
    Composer.add(Integer.valueOf(room.tradingSettings.permissions), writer);
    Composer.add(Integer.valueOf(room.haveFlag(2) ? 1 : 0), writer);
    Composer.add(Integer.valueOf(room.haveFlag(4) ? 1 : 0), writer);
    Composer.add(Integer.valueOf(room.haveFlag(8) ? 1 : 0), writer);
    Composer.add(Integer.valueOf(room.haveFlag(16) ? 1 : 0), writer);
    Composer.writeInt32(room.wallAnchor, writer);
    Composer.writeInt32(room.floorAnchor, writer);
    SerializeRoomChatConfig.parse(room.chatSettings, writer);
    SerializeRoomModerationPermissions.parse(room.modPermissions, writer);
    Composer.endPacket(writer);
    return writer;
  }
}


