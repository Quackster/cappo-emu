package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.pets.Pet;
import cappo.game.player.PlayerData;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.pets.AddPetToInventoryComposer;

public class RemovePetFromFlatParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    PetEntity petEntity = avatar.room.getRoomPetById(Main.currentPacket.readInt());
    if ((petEntity == null) || (petEntity.petData.ownerId != Main.playerData.userId)) {
      return;
    }
    Main.inventory.addPet(petEntity.petData.id, petEntity.petData);
    QueueWriter.write(Main.socket, AddPetToInventoryComposer.compose(petEntity.petData));
    avatar.room.removePet(petEntity);
  }
}


