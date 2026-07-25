package cappo.protocol.messages.events.avatar;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.game.player.messenger.MessengerFriend;
import cappo.game.player.messenger.MessengerFriendUpdate;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.avatar.ResultChangeUserNameComposer;
import cappo.protocol.messages.composers.users.NotifyUserNameChangeComposer;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChangeUserNameParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (!Main.haveFlag(4)) {
      return;
    }
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    long now = Utils.getTimestamp();
    if (Main.avatarData.lastCheckNameTry >= now) {
      return;
    }
    Main.avatarData.lastChangeNameTry = (now + 1L);
    
    String name = Main.currentPacket.readString();
    if (name.length() < 5) {
      return;
    }
    if (name.length() > 20) {
      return;
    }
    PlayerData playerData = Main.getPlayerData();
    if ((name == playerData.userName) || (name.toLowerCase().startsWith("mod-"))) {
      return;
    }
    DBResult result = new DBResult();
    try
    {
      Database.query(result, "SELECT null FROM users WHERE username = ? LIMIT 1;", new Object[] { name });
      if (result.data.next())
      {
        result.close();
        return;
      }
    }
    catch (Exception ex)
    {
      Log.printException("ChangeUserNameParser-1", ex);
      
      result.close();
      
      playerData.userName = name;
      Main.setFlag(4, false);
      QueueWriter.write(Main.socket, ResultChangeUserNameComposer.compose(0, name, new ArrayList()));
      avatar.room.sendMessage(NotifyUserNameChangeComposer.compose(playerData.userId, avatar.virtualId, name));
      for (MessengerFriend friend : playerData.messenger.getFriends())
      {
        PlayerData friendPlayer = Clients.getPlayerDataLoaded(friend.userId);
        if (friendPlayer != null)
        {
          PlayerMessenger messenger = friendPlayer.messenger;
          if (messenger.isOnline) {
            messenger.update(new MessengerFriendUpdate(playerData.userId, 0));
          }
        }
      }
    }
  }
}


