package cappo.protocol.messages.events.room.pets;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.notifications.PetLevelNotificationComposer;
import cappo.protocol.messages.composers.notifications.PetRespectFailedComposer;
import cappo.protocol.messages.composers.users.PetRespectedComposer;

public class RespectPetParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    
    PetEntity petEntity = room.getRoomPetById(Main.currentPacket.readInt());
    if (petEntity != null)
    {
      Pet pet = petEntity.petData;
      
      int petAge = (int)((Utils.getTimestamp() - pet.TimeCreated) / 86400L);
      if (petAge < 7)
      {
        QueueWriter.write(Main.socket, PetRespectFailedComposer.compose(7, petAge));
        return;
      }
      Main.dailyPetRespectPoints -= 1;
      pet.Experience += 10;
      
      room.sendMessage(PetRespectedComposer.compose(petEntity.petData.id, ++pet.Respects, pet));
      if (pet.base.checkLevel(pet)) {
        room.sendMessage(PetLevelNotificationComposer.compose(petEntity.virtualId, pet));
      }
    }
  }
}


