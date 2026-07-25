package cappo.protocol.messages.composers.inventory.pets;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.Pet;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializePet;

public class AddPetToInventoryComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Pet pet)
  {
    MessageWriter writer = new MessageWriter();
    Composer.initPacket(HEADER, writer);
    SerializePet.parse(pet, writer);
    Composer.add(Boolean.valueOf(false), writer);
    Composer.endPacket(writer);
    return writer;
  }
}


