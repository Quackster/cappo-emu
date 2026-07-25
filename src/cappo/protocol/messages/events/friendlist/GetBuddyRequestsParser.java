package cappo.protocol.messages.events.friendlist;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.friendlist.BuddyRequestsComposer;

public class GetBuddyRequestsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, BuddyRequestsComposer.compose(Main.playerData.messenger.getFriendRequests()));
  }
}


