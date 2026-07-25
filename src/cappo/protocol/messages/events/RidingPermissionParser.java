package cappo.protocol.messages.events;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.IncomingMessageEvent;

public class RidingPermissionParser
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
    petEntity.petData.ridingAll = (!petEntity.petData.ridingAll);
    petEntity.look = (petEntity.petData.petType + " " + petEntity.petData.base.raceId + " " + petEntity.petData.Color + "3 2 -1 1 4 10 0 3 -1 1");
    


    avatar.room.userUpdateNeeded(petEntity);
  }
}


