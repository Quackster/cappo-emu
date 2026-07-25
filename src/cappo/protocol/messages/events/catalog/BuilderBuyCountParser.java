package cappo.protocol.messages.events.catalog;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.catalog.BuilderBuyCountComposer;

public class BuilderBuyCountParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    QueueWriter.write(cn.socket, BuilderBuyCountComposer.compose());
  }
}


