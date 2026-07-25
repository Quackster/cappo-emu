package cappo.protocol.messages.events.users;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.UserTagsComposer;

public class GetUserTagsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    PlayerData pClient = Clients.getPlayerData(Main.currentPacket.readInt());
    if (pClient == null) {
      return;
    }
    QueueWriter.write(Main.socket, UserTagsComposer.compose(pClient));
  }
}


