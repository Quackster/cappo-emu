package cappo.protocol.messages.parsers.users;

import cappo.engine.network.MessageReader;
import cappo.game.player.data.AvatarData;

public class SetUserChatSettingMessageParser
{
  public boolean isValid;
  private boolean oldChatStyle;
  private AvatarData avatarData;
  
  public SetUserChatSettingMessageParser(MessageReader reader, AvatarData player)
  {
    if (player == null) {
      return;
    }
    this.avatarData = player;
    this.oldChatStyle = reader.readBoolean();
    this.isValid = true;
  }
  
  public void setChatStyle()
  {
    this.avatarData.oldChatStyle = this.oldChatStyle;
  }
}


