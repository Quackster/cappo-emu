package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.Pet;
import cappo.protocol.messages.Composer;

public class SerializePet
{
  public static void parse(Pet pet, MessageWriter writer)
  {
    Composer.add(Integer.valueOf(pet.id), writer);
    Composer.add(pet.name, writer);
    SerializePetfigureData.parse(pet, writer);
    Composer.add(Integer.valueOf(pet.level), writer);
  }
}


