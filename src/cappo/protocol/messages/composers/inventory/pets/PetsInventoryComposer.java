package cappo.protocol.messages.composers.inventory.pets;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.Pet;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializePet;
import java.util.Collection;

public class PetsInventoryComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int virtualId, Collection<Pet> InventoryPets)
  {
    MessageWriter writer = new MessageWriter(100 + InventoryPets.size() * 100);
    Composer.initPacket(HEADER, writer);
    Composer.add(Integer.valueOf(1), writer);
    Composer.add(Integer.valueOf(1), writer);
    Composer.add(Integer.valueOf(InventoryPets.size()), writer);
    for (Pet pet : InventoryPets) {
      SerializePet.parse(pet, writer);
    }
    Composer.endPacket(writer);
    return writer;
  }
}


