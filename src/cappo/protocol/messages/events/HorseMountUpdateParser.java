package cappo.protocol.messages.events;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.games.snowwar.Direction8;
import cappo.game.pets.Pet;
import cappo.game.roomeffects.special.RidingEffect;
import cappo.game.roomeffects.special.UserSpecialEffect;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.IncomingMessageEvent;

public class HorseMountUpdateParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    PetEntity petEntity = avatar.room.getRoomPetById(Main.currentPacket.readInt());
    if ((petEntity == null) || (petEntity.petData.petType != 15)) {
      return;
    }
    boolean mount = Main.currentPacket.readBoolean();
    if (mount)
    {
      if ((!petEntity.petData.ridingAll) && (avatar.controllerLevel < 4)) {
        return;
      }
      if ((petEntity.ridingEntity != null) || (avatar.ridingEntity != null)) {
        return;
      }
      int frontX = petEntity.x + petEntity.RotBody.getDiffX();
      int frontY = petEntity.y + petEntity.RotBody.getDiffY();
      if (((avatar.x == frontX) && (avatar.y == frontY)) || ((avatar.x == petEntity.x) && (avatar.y == petEntity.y)))
      {
        avatar.allowOverride = true;
        avatar.moveTo(petEntity.x, petEntity.y);
        
        petEntity.ridingEntity = avatar;
        avatar.ridingEntity = petEntity;
        if ((avatar.userSpecialEffect == null) || (avatar.userSpecialEffect.effectId != 77)) {
          avatar.userSpecialEffect = new RidingEffect(avatar, (short)77);
        }
      }
      else
      {
        avatar.moveTo(frontX, frontY);
      }
    }
    else
    {
      if ((petEntity.ridingEntity == null) || (avatar.ridingEntity == null)) {
        return;
      }
      petEntity.ridingEntity = null;
      avatar.ridingEntity = null;
    }
    avatar.room.userUpdateNeeded(petEntity);
  }
}


