package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.game.player.messenger.MessengerFriendRequest;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class BuddyRequestsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<MessengerFriendRequest> collection)
  {
    int len = collection.size();
    MessageWriter ClientMessage = new MessageWriter(40 + collection.size() * 40);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(len), ClientMessage);
    Composer.add(Integer.valueOf(len), ClientMessage);
    for (MessengerFriendRequest request : collection)
    {
      Composer.add(Integer.valueOf(request.userid), ClientMessage);
      Composer.add(request.username, ClientMessage);
      Composer.add(Integer.toString(request.userid), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


