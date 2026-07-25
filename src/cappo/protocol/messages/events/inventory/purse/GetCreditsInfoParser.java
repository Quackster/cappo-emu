package cappo.protocol.messages.events.inventory.purse;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.purse.CreditBalanceComposer;

public class GetCreditsInfoParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, CreditBalanceComposer.compose(Main.credits));
  }
}


