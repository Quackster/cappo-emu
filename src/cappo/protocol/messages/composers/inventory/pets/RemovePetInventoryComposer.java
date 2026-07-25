package cappo.protocol.messages.composers.inventory.pets;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RemovePetInventoryComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int PetId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(PetId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


