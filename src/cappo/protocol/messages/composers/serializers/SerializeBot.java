package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.bots.RentalBot;
import cappo.game.player.AvatarLook;
import cappo.protocol.messages.Composer;

public class SerializeBot
{
  public static void parse(MessageWriter ClientMessage, RentalBot bot)
  {
    Composer.add(Integer.valueOf(bot.id), ClientMessage);
    Composer.add(bot.name, ClientMessage);
    Composer.add(bot.motto, ClientMessage);
    Composer.add(bot.gender, ClientMessage);
    Composer.add(bot.botLook.toString(), ClientMessage);
  }
}


