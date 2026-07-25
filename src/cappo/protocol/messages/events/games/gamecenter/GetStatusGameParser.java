package cappo.protocol.messages.events.games.gamecenter;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.games.gamecenter.StatusGameComposer;

public class GetStatusGameParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int gameTypeId = Main.currentPacket.readInt();
    QueueWriter.write(Main.socket, StatusGameComposer.compose(gameTypeId, 0));
  }
}


