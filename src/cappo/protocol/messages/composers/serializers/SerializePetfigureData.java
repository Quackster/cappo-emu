package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.protocol.messages.Composer;

public class SerializePetfigureData
{
  public static void parse(Pet pet, MessageWriter writer)
  {
    Composer.add(Short.valueOf(pet.petType), writer);
    Composer.add(Integer.valueOf(2), writer);
    Composer.add(pet.Color, writer);
    Composer.add(Short.valueOf(pet.base.raceId), writer);
    Composer.add(Integer.valueOf(1), writer);
    
    Composer.add(Integer.valueOf(10), writer);
    Composer.add(Integer.valueOf(10), writer);
    Composer.add(Integer.valueOf(10), writer);
  }
}


