package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.games.snowwar.AccountGameStatusComposer;

public class GetAccountGameStatusParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, AccountGameStatusComposer.compose(Main.currentPacket.readInt()));
  }
}


