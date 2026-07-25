package cappo.protocol.messages.events.friendlist;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.MessengerFriendRequest;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.friendlist.MessengerErrorComposer;
import cappo.protocol.messages.composers.friendlist.NewBuddyRequestComposer;

public class RequestBuddyParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (Main.playerData.messenger.isFull())
    {
      QueueWriter.write(Main.socket, MessengerErrorComposer.compose(0, 1));
      return;
    }
    String UserName = Main.currentPacket.readString();
    
    PlayerData reqPlayer = Clients.getPlayerData(UserName);
    if (reqPlayer == null)
    {
      QueueWriter.write(Main.socket, MessengerErrorComposer.compose(0, 4));
      return;
    }
    MessengerFriendRequest req = Main.playerData.messenger.pickRequest(reqPlayer.userId);
    if (req != null)
    {
      Main.playerData.messenger.addFriend(reqPlayer);
      return;
    }
    if (reqPlayer.messenger.isFull())
    {
      QueueWriter.write(Main.socket, MessengerErrorComposer.compose(0, 2));
      return;
    }
    reqPlayer.messenger.addFriendRequest(Main.playerData.userId, Main.playerData.userName, true);
    if (reqPlayer.connection != null) {
      QueueWriter.write(reqPlayer.connection.socket, NewBuddyRequestComposer.compose(Main.playerData.userId, Main.playerData.userName));
    }
  }
}


