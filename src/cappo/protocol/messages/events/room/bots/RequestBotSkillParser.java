package cappo.protocol.messages.events.room.bots;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.bots.RentalBot;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.RentalBotEntity;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.bots.BotSkillComposer;

public class RequestBotSkillParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RentalBotEntity botEntity = avatar.room.getRoomBotById(Main.currentPacket.readInt());
    if (botEntity == null) {
      return;
    }
    RentalBot bot = botEntity.botData;
    if (bot.ownerId != Main.playerData.userId) {
      return;
    }
    int propId = Main.currentPacket.readInt();
    if (2 == propId)
    {
      String data = "";
      for (String chat : bot.speeches) {
        data = data + chat + "\r";
      }
      data = data + ";#;" + (bot.chatAuto ? 1 : 0) + ";#;" + bot.chatDelay;
      
      QueueWriter.write(Main.socket, BotSkillComposer.compose(bot.id, propId, data));
    }
    else if (5 == propId)
    {
      QueueWriter.write(Main.socket, BotSkillComposer.compose(bot.id, propId, bot.name));
    }
  }
}


