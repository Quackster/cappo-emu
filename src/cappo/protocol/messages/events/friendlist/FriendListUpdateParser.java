package cappo.protocol.messages.events.friendlist;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;

public class FriendListUpdateParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, Main.playerData.messenger.getFriendUpdstes());
  }
}


