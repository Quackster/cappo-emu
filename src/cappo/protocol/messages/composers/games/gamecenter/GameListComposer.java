package cappo.protocol.messages.composers.games.gamecenter;

import cappo.engine.network.MessageWriter;
import cappo.game.games.GameBase;
import cappo.game.games.GamesManager;
import cappo.protocol.messages.Composer;
import java.util.List;

public class GameListComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(GamesManager.games.size()), ClientMessage);
    for (GameBase game : GamesManager.games)
    {
      Composer.add(Integer.valueOf(game.gameId), ClientMessage);
      Composer.add(game.gameName, ClientMessage);
      Composer.add(game.bgColor, ClientMessage);
      Composer.add(game.textColor, ClientMessage);
      Composer.add(game.imagesPath, ClientMessage);
      Composer.add("", ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


