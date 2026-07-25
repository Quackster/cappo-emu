package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.pets.PetCommandsComposer;

public class GetPetCommandsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    PetEntity User = avatar.room.getRoomPetById(Main.currentPacket.readInt());
    if (User != null) {
      QueueWriter.write(Main.socket, PetCommandsComposer.compose(User.petData));
    }
  }
}


