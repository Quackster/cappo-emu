package cappo.protocol.messages.events.catalog;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class PurchaseVipMembershipExtensionParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.currentPacket.readInt();
  }
}


