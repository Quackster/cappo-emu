package cappo.protocol.messages.events.recycler;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.recycler.RecyclerPrizesComposer;

public class GetRecyclerPrizesParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, RecyclerPrizesComposer.compose());
  }
}


