package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.PetBase;
import cappo.protocol.messages.Composer;
import java.util.Map;

public class SellablePetBreedsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String PetType)
  {
    MessageWriter ClientMessage = new MessageWriter();
    short Type = Short.parseShort(PetType.substring(6));
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(PetType, ClientMessage);
    if ((Type >= 0) && (Type < 27))
    {
      Composer.add(Integer.valueOf(cappo.game.pets.Pet.PETS[Type].races.size()), ClientMessage);
      for (PetBase race : cappo.game.pets.Pet.PETS[Type].races.values())
      {
        Composer.add(Short.valueOf(Type), ClientMessage);
        Composer.add(Short.valueOf(race.raceId), ClientMessage);
        Composer.add(Short.valueOf(race.raceId), ClientMessage);
        Composer.add(Boolean.valueOf(true), ClientMessage);
        Composer.add(Boolean.valueOf(false), ClientMessage);
      }
    }
    else
    {
      Composer.add(Integer.valueOf(0), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


