package cappo.game.games;

import cappo.game.player.SnowWarPlayerData;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GamesLeaderboard
{
  public static final Map<Integer, GamesLeaderboard> leaderboards = new ConcurrentHashMap();
  public final int gameId;
  public List<SnowWarPlayerData> rankedList;
  
  public GamesLeaderboard(int id)
  {
    this.gameId = id;
  }
}


