package cappo.protocol.messages.events.catalog;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.catalog.HabboClubExtendOfferComposer;

public class GetHabboClubExtendOfferParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, HabboClubExtendOfferComposer.compose());
  }
}


