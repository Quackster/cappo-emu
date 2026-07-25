package cappo.protocol.messages.events.room.action;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.DoorBellNoAnswerComposer;
import cappo.protocol.messages.composers.room.session.FlatAccessibleComposer;

public class LetUserInParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    String userName = Main.currentPacket.readString();
    PlayerData client = Clients.getPlayerData(userName);
    if ((client == null) || (client.connection == null)) {
      return;
    }
    Connection clientCn = client.connection;
    if (clientCn == null) {
      return;
    }
    if (clientCn.avatar != null) {
      return;
    }
    if (clientCn.avatarData.LoadingRoom != 0) {
      return;
    }
    if (Main.currentPacket.readBoolean()) {
      QueueWriter.writeAndFlush(clientCn.socket, FlatAccessibleComposer.compose(""));
    } else {
      QueueWriter.writeAndFlush(clientCn.socket, DoorBellNoAnswerComposer.compose());
    }
  }
}


