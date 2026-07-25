package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.game.player.messenger.MessengerFriend;
import cappo.game.player.messenger.MessengerFriendCategory;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeFriend;
import java.util.Collection;

public class MessengerInitComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(PlayerMessenger messenger)
  {
    Collection<MessengerFriendCategory> categories = messenger.getCategories();
    Collection<MessengerFriend> friends = messenger.getFriends();
    
    MessageWriter ClientMessage = new MessageWriter(100 + (categories.size() + 30) + friends.size() * 250);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(messenger.getLimitFriends()), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(600), ClientMessage);
    Composer.add(Integer.valueOf(1200), ClientMessage);
    Composer.add(Integer.valueOf(categories.size()), ClientMessage);
    for (MessengerFriendCategory category : categories)
    {
      Composer.add(Integer.valueOf(category.id), ClientMessage);
      Composer.add(category.name, ClientMessage);
    }
    int friendSize = friends.size();
    if (friendSize > messenger.getLimitFriends()) {
      friendSize = messenger.getLimitFriends();
    }
    Composer.add(Integer.valueOf(friendSize), ClientMessage);
    for (MessengerFriend friend : friends)
    {
      if (friendSize-- < 1) {
        break;
      }
      SerializeFriend.parse(ClientMessage, friend);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


