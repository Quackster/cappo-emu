package cappo.protocol.messages.events;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.IncomingMessageEvent;

public class RemoveHorseSaddleParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    PetEntity petEntity = avatar.room.getRoomPetById(Main.currentPacket.readInt());
    if ((petEntity == null) || (petEntity.petData.petType != 15)) {
      return;
    }
    if ((!petEntity.petData.haveSaddle) || (petEntity.ridingEntity != null)) {
      return;
    }
    petEntity.petData.haveSaddle = false;
    Main.inventoryAddFloorItem(petEntity.petData.saddleFurni);
    petEntity.look = (petEntity.petData.petType + " " + petEntity.petData.base.raceId + " " + petEntity.petData.Color);
    avatar.room.userUpdateNeeded(petEntity);
  }
}


