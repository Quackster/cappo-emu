package cappo.protocol.messages.events.inventory.bots;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.inventory.PlayerInventory;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.bots.BotsInventoryComposer;

public class RequestBotInventoryParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, BotsInventoryComposer.compose(Main.inventory.getBots()));
  }
}


