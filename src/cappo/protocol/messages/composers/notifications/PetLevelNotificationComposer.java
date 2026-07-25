package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.Pet;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializePetfigureData;

public class PetLevelNotificationComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int VirtualId, Pet pet)
  {
    MessageWriter writer = new MessageWriter();
    Composer.initPacket(HEADER, writer);
    Composer.add(Integer.valueOf(VirtualId), writer);
    Composer.add(pet.name, writer);
    Composer.add(Integer.valueOf(pet.level), writer);
    SerializePetfigureData.parse(pet, writer);
    Composer.endPacket(writer);
    return writer;
  }
}


