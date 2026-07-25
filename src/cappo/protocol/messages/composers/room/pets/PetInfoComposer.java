package cappo.protocol.messages.composers.room.pets;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.Utils;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.Composer;

public class PetInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Pet pet)
  {
    MessageWriter ClientMessage = new MessageWriter(1000);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(pet.id), ClientMessage);
    Composer.add(pet.name, ClientMessage);
    Composer.add(Integer.valueOf(pet.level), ClientMessage);
    Composer.add(Integer.valueOf(20), ClientMessage);
    Composer.add(Integer.valueOf(pet.Experience), ClientMessage);
    Composer.add(Integer.valueOf(PetBase.ExperienceLevels[pet.level]), ClientMessage);
    Composer.add(Integer.valueOf(pet.Energy), ClientMessage);
    Composer.add(Integer.valueOf(PetBase.MaxEnergyLevels[pet.level]), ClientMessage);
    Composer.add(Integer.valueOf(pet.happiness), ClientMessage);
    Composer.add(Integer.valueOf(PetBase.MaxHappinessLevels[pet.level]), ClientMessage);
    Composer.add(Integer.valueOf(pet.Respects), ClientMessage);
    Composer.add(Integer.valueOf(pet.ownerId), ClientMessage);
    Composer.add(Long.valueOf((Utils.getTimestamp() - pet.TimeCreated) / 86400L), ClientMessage);
    Composer.add(pet.ownerName, ClientMessage);
    Composer.add(Short.valueOf(pet.base.raceId), ClientMessage);
    Composer.add(Boolean.valueOf(pet.haveSaddle), ClientMessage);
    Composer.add(Boolean.valueOf(pet.petEntity.ridingEntity != null), ClientMessage);
    

    Composer.add(Integer.valueOf(3), ClientMessage);
    
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(3), ClientMessage);
    Composer.add(Integer.valueOf(5), ClientMessage);
    

    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


