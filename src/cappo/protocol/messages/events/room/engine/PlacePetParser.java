package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.pets.Pet;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.pets.RemovePetInventoryComposer;
import cappo.protocol.messages.composers.room.pets.PetPlacingErrorComposer;
import java.util.Map;

public class PlacePetParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    if ((!room.roomData.haveFlag(2)) || (
      (avatar.controllerLevel != 1) && 
      (avatar.controllerLevel < 4)))
    {
      QueueWriter.write(Main.socket, PetPlacingErrorComposer.compose(1));
      return;
    }
    if (room.PetCounter >= 5)
    {
      QueueWriter.write(Main.socket, PetPlacingErrorComposer.compose(2));
      return;
    }
    int petId = Main.currentPacket.readInt();
    

    int xy = Main.currentPacket.readInt() + Main.currentPacket.readInt() * room.model.widthX;
    if (!room.canPlacePet(xy))
    {
      QueueWriter.write(Main.socket, PetPlacingErrorComposer.compose(4));
      return;
    }
    Pet pet = Main.inventory.removePet(petId);
    if (pet == null) {
      return;
    }
    Float z = (Float)room.squareAbsoluteHeight.get(Integer.valueOf(xy));
    if (z == null) {
      z = Float.valueOf(0.0F);
    }
    room.deployPet(pet, xy, z.floatValue());
    
    QueueWriter.write(Main.socket, RemovePetInventoryComposer.compose(pet.id));
  }
}


