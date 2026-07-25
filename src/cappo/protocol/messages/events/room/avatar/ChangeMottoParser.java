package cappo.protocol.messages.events.room.avatar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.engine.UserChangeComposer;

public class ChangeMottoParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    String newMotto = Main.currentPacket.readString();
    if (newMotto.length() > 38) {
      newMotto = newMotto.substring(0, 38);
    }
    PlayerData playerData = Main.getPlayerData();
    if (playerData.motto.equals(newMotto)) {
      return;
    }
    playerData.motto = newMotto;
    
    avatar.room.sendMessage(UserChangeComposer.compose(avatar.virtualId, playerData.avatarLook.toString(), playerData.sex, playerData.motto, playerData.AchievementsScore));
  }
}


