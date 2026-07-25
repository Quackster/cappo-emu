package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.MessengerFriend;
import cappo.game.player.messenger.MessengerFriendUpdate;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeFriend;
import java.util.Collection;

public class FriendsUpdatesComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<MessengerFriendUpdate> updates, PlayerData playerData)
  {
    MessageWriter ClientMessage = new MessageWriter(1000 + updates.size() * 300);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(ClientMessage.setSaved(Integer.valueOf(0)), ClientMessage);
    int i = 0;
    for (MessengerFriendUpdate update : updates) {
      if (update.type == -1)
      {
        i++;
        Composer.add(Integer.valueOf(update.type), ClientMessage);
        Composer.add(Integer.valueOf(update.userId), ClientMessage);
      }
      else
      {
        MessengerFriend friend = playerData.messenger.getFriend(update.userId);
        if (friend != null)
        {
          i++;
          
          Composer.add(Integer.valueOf(update.type), ClientMessage);
          SerializeFriend.parse(ClientMessage, friend);
        }
      }
    }
    ClientMessage.writeSaved(Integer.valueOf(i));
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


