package cappo.game.games;

import cappo.game.games.basejump.BaseJump;
import cappo.game.games.snowwar.SnowWar;
import java.util.ArrayList;
import java.util.List;

public class GamesManager
{
  public static List<GameBase> games = new ArrayList();
  
  public static void initManager()
  {
    addGame(new SnowWar(), true);
    addGame(new BaseJump(), true);
  }
  
  public static void addGame(GameBase game, boolean enabled)
  {
    games.add(game);
    game.isEnabled = enabled;
  }
}


