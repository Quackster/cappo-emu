package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.MessengerFriend;
import cappo.protocol.messages.Composer;

public class SerializeFriend
{
  public static void parse(MessageWriter ClientMessage, MessengerFriend friend)
  {
    PlayerData friendPlayer = Clients.getPlayerData(friend.userId);
    
    Composer.add(Integer.valueOf(friend.userId), ClientMessage);
    Composer.add(friendPlayer.userName, ClientMessage);
    Composer.add(Integer.valueOf(friendPlayer.sex), ClientMessage);
    if (friendPlayer.connection != null)
    {
      Composer.add(Boolean.valueOf(true), ClientMessage);
      Composer.add(Boolean.valueOf(friendPlayer.connection.avatar != null), ClientMessage);
    }
    else
    {
      Composer.add(Boolean.valueOf(false), ClientMessage);
      Composer.add(Boolean.valueOf(false), ClientMessage);
    }
    Composer.add(friendPlayer.avatarLook.toString(), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(friendPlayer.motto, ClientMessage);
    Composer.add(friendPlayer.getRealName(), ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.writeInt16(friend.friendType, ClientMessage);
  }
}


