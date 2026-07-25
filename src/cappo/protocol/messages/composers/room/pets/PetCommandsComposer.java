package cappo.protocol.messages.composers.room.pets;

import cappo.engine.network.MessageWriter;
import cappo.game.pets.Pet;
import cappo.protocol.messages.Composer;

public class PetCommandsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Pet pet)
  {
    int cmds = 32;
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(pet.id), ClientMessage);
    Composer.add(Integer.valueOf(cmds), ClientMessage);
    for (int i = 0; i < cmds; i++) {
      Composer.add(Integer.valueOf(i), ClientMessage);
    }
    if (pet.level < cmds) {
      cmds = pet.level;
    }
    Composer.add(Integer.valueOf(cmds), ClientMessage);
    for (int i = 0; i < cmds; i++) {
      Composer.add(Integer.valueOf(i), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


