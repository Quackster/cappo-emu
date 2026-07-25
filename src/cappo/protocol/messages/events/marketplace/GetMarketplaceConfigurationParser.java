package cappo.protocol.messages.events.marketplace;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.marketplace.MarketplaceConfigComposer;

public class GetMarketplaceConfigurationParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, MarketplaceConfigComposer.compose());
  }
}


