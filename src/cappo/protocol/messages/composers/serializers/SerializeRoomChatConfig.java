package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.settings.ChatSettings;
import cappo.protocol.messages.Composer;

public class SerializeRoomChatConfig
{
  public static void parse(ChatSettings settings, MessageWriter writer)
  {
    Composer.writeInt32(settings.chatMode, writer);
    Composer.writeInt32(settings.chatBubbleWidth, writer);
    Composer.writeInt32(settings.chatScrollSpeed, writer);
    Composer.writeInt32(settings.chatHearingDistance, writer);
    Composer.writeInt32(settings.chatFloodSensitivity, writer);
  }
}


