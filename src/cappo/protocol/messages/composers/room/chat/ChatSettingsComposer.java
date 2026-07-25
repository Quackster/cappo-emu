package cappo.protocol.messages.composers.room.chat;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.settings.ChatSettings;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeRoomChatConfig;

public class ChatSettingsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(ChatSettings settings)
  {
    MessageWriter writer = new MessageWriter();
    Composer.initPacket(HEADER, writer);
    SerializeRoomChatConfig.parse(settings, writer);
    Composer.endPacket(writer);
    return writer;
  }
}


