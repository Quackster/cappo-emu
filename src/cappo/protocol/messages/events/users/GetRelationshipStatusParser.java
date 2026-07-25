package cappo.protocol.messages.events.users;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.RelationshipStatusComposer;

public class GetRelationshipStatusParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    PlayerData player = Clients.getPlayerData(Main.currentPacket.readInt());
    if (player != null) {
      QueueWriter.write(Main.socket, RelationshipStatusComposer.compose(player));
    }
  }
}


