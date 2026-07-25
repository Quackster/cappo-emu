package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.game.player.data.AvatarData;
import cappo.protocol.messages.Composer;

public class UserSettingsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(AvatarData avatarData)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.writeInt32(avatarData.volume1, ClientMessage);
    Composer.writeInt32(avatarData.volume2, ClientMessage);
    Composer.writeInt32(avatarData.volume3, ClientMessage);
    Composer.writeBoolean(avatarData.oldChatStyle, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


