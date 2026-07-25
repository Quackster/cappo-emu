package cappo.protocol.messages.events;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.IncomingMessageEvent;

public class PutHorseSaddleParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    RoomTask room = avatar.room;
    
    FloorItem flooritem = room.getFloorItem(Main.currentPacket.readInt());
    if (flooritem == null) {
      return;
    }
    PetEntity petEntity = room.getRoomPetById(Main.currentPacket.readInt());
    if ((petEntity == null) || (petEntity.petData.petType != 15)) {
      return;
    }
    room.removeFloorItem(flooritem, Main.playerData.userId);
    petEntity.petData.saddleFurni = flooritem;
    petEntity.petData.haveSaddle = true;
    petEntity.ridingEntity = null;
    petEntity.look = (petEntity.petData.petType + " " + petEntity.petData.base.raceId + " " + petEntity.petData.Color + "3 2 -1 1 4 10 0 3 -1 1");
    


    room.userUpdateNeeded(petEntity);
  }
}


