package cappo.protocol.messages.events.users;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.UserProfileInfoComposer;

public class GetExtendedProfileParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
    throws Exception
  {
    PlayerData client = Clients.getPlayerData(cn.currentPacket.readInt());
    if (client == null) {
      return;
    }
    PlayerData playerData = cn.getPlayerData();
    
    QueueWriter.write(cn.socket, UserProfileInfoComposer.compose(client, playerData.messenger.haveFriend(client.userId), playerData.messenger.haveRequest(playerData.userId)));
  }
}


