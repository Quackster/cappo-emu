package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.Pet;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializePet;

public class PetReceivedMessageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(boolean petBought, Pet pet)
  {
    MessageWriter writer = new MessageWriter();
    Composer.initPacket(HEADER, writer);
    Composer.writeBoolean(petBought, writer);
    SerializePet.parse(pet, writer);
    Composer.endPacket(writer);
    return writer;
  }
}


