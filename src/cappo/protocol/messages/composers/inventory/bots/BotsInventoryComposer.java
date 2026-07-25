package cappo.protocol.messages.composers.inventory.bots;

import cappo.engine.network.MessageWriter;
import cappo.game.bots.RentalBot;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeBot;
import java.util.Collection;

public class BotsInventoryComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<RentalBot> inventoryBots)
  {
    MessageWriter ClientMessage = new MessageWriter(500 + inventoryBots.size() * 500);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(inventoryBots.size()), ClientMessage);
    for (RentalBot bot : inventoryBots) {
      SerializeBot.parse(ClientMessage, bot);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


