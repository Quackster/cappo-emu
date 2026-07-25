package cappo.protocol.messages.events.friendlist;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.MessengerFriend;
import cappo.game.player.messenger.MessengerFriendUpdate;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;

public class SetRelationshipStatusParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    PlayerMessenger messenger = Main.playerData.messenger;
    MessengerFriend friend = messenger.getFriend(Main.currentPacket.readInt());
    if (friend == null) {
      return;
    }
    messenger.removeRelationship(friend.friendType, friend.userId);
    
    friend.friendType = Main.currentPacket.readInt();
    if (friend.friendType != 0) {
      messenger.setRelationshipStatus(friend.friendType, friend);
    }
    friend.needUpdate = true;
    
    messenger.update(new MessengerFriendUpdate(friend.userId, 0));
    QueueWriter.write(Main.socket, messenger.getFriendUpdstes());
  }
}


