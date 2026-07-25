package cappo.protocol.messages.events.inventory.furni;

import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.inventory.PlayerInventory;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.furni.FurniListComposer;

public class RequestFurniInventoryParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    for (MessageWriter packet : FurniListComposer.compose(Main.inventory.getObjects(), Main.inventory.getItems())) {
      if (packet != null) {
        QueueWriter.write(Main.socket, packet);
      }
    }
  }
}


