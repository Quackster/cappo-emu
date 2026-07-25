package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.bots.RentalBot;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.bots.RemoveBotInventoryComposer;
import cappo.protocol.messages.composers.room.bots.BotErrorComposer;
import java.util.Map;

public class PlaceRentalBotParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if (avatar.controllerLevel < 4) {
      return;
    }
    RoomTask room = avatar.room;
    if (room.PetCounter >= 5)
    {
      QueueWriter.write(Main.socket, BotErrorComposer.compose(2));
      return;
    }
    int botId = Main.currentPacket.readInt();
    

    int xy = Main.currentPacket.readInt() + Main.currentPacket.readInt() * room.model.widthX;
    if (!room.canPlacePet(xy))
    {
      QueueWriter.write(Main.socket, BotErrorComposer.compose(3));
      return;
    }
    RentalBot bot = Main.inventory.removeBot(botId);
    if (bot == null) {
      return;
    }
    Float z = (Float)room.squareAbsoluteHeight.get(Integer.valueOf(xy));
    if (z == null) {
      z = Float.valueOf(0.0F);
    }
    room.deployBot(bot, xy, z.floatValue(), bot.botLook, bot.gender);
    
    QueueWriter.write(Main.socket, RemoveBotInventoryComposer.compose(bot.id));
  }
}


