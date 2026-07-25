package cappo.protocol.messages.events.register;

import cappo.engine.network.CappoServer;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.engine.UserChangeComposer;

public class UpdateFigureDataParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    String Gender = Main.currentPacket.readString();
    String SelectedLook = Main.currentPacket.readString();
    if (!AvatarLook.validateLook(SelectedLook))
    {
      CappoServer.close(Main.socket);
      return;
    }
    PlayerData playerData = Main.getPlayerData();
    playerData.avatarLook = new AvatarLook(SelectedLook);
    playerData.sex = (Gender.equalsIgnoreCase("M") ? 1 : 0);
    
    QueueWriter.write(Main.socket, UserChangeComposer.compose(-1, playerData.avatarLook.toString(), playerData.sex, playerData.motto, playerData.AchievementsScore));
    avatar.room.sendMessage(UserChangeComposer.compose(avatar.virtualId, playerData.avatarLook.toString(), playerData.sex, playerData.motto, playerData.AchievementsScore));
  }
}


