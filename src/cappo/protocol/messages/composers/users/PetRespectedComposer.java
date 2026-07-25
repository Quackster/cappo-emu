package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.Pet;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializePet;

public class PetRespectedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Id, int respects, Pet pet)
  {
    MessageWriter writer = new MessageWriter();
    Composer.initPacket(HEADER, writer);
    Composer.add(Integer.valueOf(Id), writer);
    Composer.add(Integer.valueOf(respects), writer);
    SerializePet.parse(pet, writer);
    Composer.endPacket(writer);
    return writer;
  }
}


