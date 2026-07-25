package cappo.protocol.messages.composers.users;

import cappo.engine.Server;
import cappo.engine.network.MessageWriter;
import cappo.game.collections.Utils;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.Composer;
import java.text.SimpleDateFormat;

public class UserProfileInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(PlayerData client, boolean isFriend, boolean pendingFriend)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(client.userId), ClientMessage);
    Composer.add(client.userName, ClientMessage);
    Composer.add(client.avatarLook.toString(), ClientMessage);
    Composer.add(client.motto, ClientMessage);
    Composer.add(Server.date.format(Utils.GetDate(client.registerDate * 1000L)), ClientMessage);
    Composer.add(Integer.valueOf(client.AchievementsScore), ClientMessage);
    Composer.add(Integer.valueOf(client.messenger.friendsCount()), ClientMessage);
    Composer.add(Boolean.valueOf(isFriend), ClientMessage);
    Composer.add(Boolean.valueOf(pendingFriend), ClientMessage);
    Composer.add(Boolean.valueOf(client.connection != null), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add("Cappo Java", ClientMessage);
    Composer.add("E001", ClientMessage);
    Composer.add("100", ClientMessage);
    Composer.add("100", ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    
    Composer.add(Long.valueOf(Utils.getTimestamp() - client.lastVisit), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


