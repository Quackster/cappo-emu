package cappo.protocol.messages.events.inventory.pets;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.pets.PetsInventoryComposer;

public class RequestPetInventoryParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, PetsInventoryComposer.compose(Main.avatar.virtualId, Main.inventory.getPets()));
  }
}


