package cappo.protocol.messages.composers.inventory.bots;

import cappo.engine.network.MessageWriter;
import cappo.game.bots.RentalBot;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeBot;

public class AddBotToInventoryComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(RentalBot bot)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    SerializeBot.parse(ClientMessage, bot);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


