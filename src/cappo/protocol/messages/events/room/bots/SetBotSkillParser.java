package cappo.protocol.messages.events.room.bots;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.bots.RentalBot;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.chat.wf.WordFilter;
import cappo.game.roomengine.chat.wf.WordFilterAction;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.RentalBotEntity;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.action.UserDanceComposer;
import cappo.protocol.messages.composers.room.bots.BotErrorComposer;
import cappo.protocol.messages.composers.room.engine.UserChangeComposer;
import cappo.protocol.messages.composers.users.NotifyUserNameChangeComposer;
import java.util.List;

public class SetBotSkillParser
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
    String value = Main.currentPacket.readString();
    
    WordFilterAction action = WordFilter.getAction(value);
    if ((action != null) && (action.run(Main))) {
      return;
    }
    if (4 == propId)
    {
      bot.danceEnabled = (!bot.danceEnabled);
      int danceId = bot.danceEnabled ? 1 : 0;
      bot.botEntity.room.sendMessage(UserDanceComposer.compose(bot.botEntity.virtualId, danceId));
    }
    else if (3 == propId)
    {
      bot.walkRandomEnabled = (!bot.walkRandomEnabled);
    }
    else if (1 == propId)
    {
      setBotLook(bot, Main.playerData);
    }
    else if (5 == propId)
    {
      if (value.length() < 5)
      {
        QueueWriter.write(Main.socket, BotErrorComposer.compose(4));
        return;
      }
      if (value.length() > 15)
      {
        QueueWriter.write(Main.socket, BotErrorComposer.compose(4));
        return;
      }
      if (value.toLowerCase().startsWith("mod-"))
      {
        QueueWriter.write(Main.socket, BotErrorComposer.compose(4));
        return;
      }
      setBotName(bot, value);
    }
    else if (2 == propId)
    {
      parseChatConfiguration(bot, value);
    }
  }
  
  private void setBotLook(RentalBot bot, PlayerData playerData)
  {
    bot.botLook = playerData.avatarLook;
    bot.gender = (playerData.sex == 1 ? "M" : "F");
    bot.botEntity.room.sendMessage(UserChangeComposer.compose(bot.botEntity.virtualId, bot.botLook.toString(), bot.gender.equals("M") ? 1 : 0, bot.motto, 0));
  }
  
  private void setBotName(RentalBot bot, String name)
  {
    bot.name = name;
    bot.botEntity.room.sendMessage(NotifyUserNameChangeComposer.compose(-1, bot.botEntity.virtualId, bot.name));
  }
  
  private void parseChatConfiguration(RentalBot bot, String data)
  {
    String[] tmp;
    if (data.indexOf(";#;") == -1) {
      tmp = data.split(";");
    } else {
      tmp = data.split(";#;");
    }
    bot.speeches.clear();
    if ((tmp.length == 3) || (tmp.length == 4))
    {
      for (String line : tmp[0].split("\r")) {
        if ((!line.isEmpty()) && (line.length() <= 100)) {
          bot.speeches.add(line);
        }
      }
      bot.chatAuto = ((tmp[1].toLowerCase().equals("true")) || (tmp[1].equals("1")));
      bot.chatDelay = Integer.parseInt(tmp[2]);
      if (bot.chatDelay < 7) {
        bot.chatDelay = 7;
      }
    }
  }
}


