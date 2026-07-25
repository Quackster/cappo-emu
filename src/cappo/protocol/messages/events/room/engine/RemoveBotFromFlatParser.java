package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.bots.RentalBot;
import cappo.game.player.PlayerData;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.RentalBotEntity;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.bots.AddBotToInventoryComposer;

public class RemoveBotFromFlatParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    
    RentalBotEntity botEntity = room.getRoomBotById(Main.currentPacket.readInt());
    if ((botEntity != null) && (botEntity.botData.ownerId == Main.playerData.userId))
    {
      Main.inventory.addBot(botEntity.botData.id, botEntity.botData);
      QueueWriter.write(Main.socket, AddBotToInventoryComposer.compose(botEntity.botData));
      room.removeBot(botEntity);
    }
  }
}


