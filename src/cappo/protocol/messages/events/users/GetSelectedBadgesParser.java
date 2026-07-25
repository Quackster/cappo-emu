package cappo.protocol.messages.events.users;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.UserBadgesComposer;
import java.util.Map;

public class GetSelectedBadgesParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    PlayerData pClient = Clients.getPlayerDataLoaded(Main.currentPacket.readInt());
    if ((pClient == null) || (pClient.connection == null)) {
      return;
    }
    QueueWriter.write(Main.socket, UserBadgesComposer.compose(pClient.userId, pClient.connection.badgesSelected.values()));
  }
}


