package cappo.game.games.snowwar.tasks;

import cappo.game.games.snowwar.SnowWar;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.composers.games.snowwar.GameEndingComposer;
import java.util.Map;

public class SnowArenaEnd
{
  public static void exec(SnowWarRoom room)
  {
    room.Winner = 0;
    for (int TeamId : SnowWar.TEAMS)
    {
      if (room.Winner == 0) {
        room.Winner = TeamId;
      }
      if (room.TeamScore[(TeamId - 1)] == room.TeamScore[(room.Winner - 1)])
      {
        room.Result = 2;
      }
      else if (room.TeamScore[(TeamId - 1)] > room.TeamScore[(room.Winner - 1)])
      {
        room.Result = 1;
        room.Winner = TeamId;
      }
    }
    if (room.Result == 2) {
      room.Winner = 0;
    }
    for (HumanGameObject player : room.players.values())
    {
      if (room.MostHits == null) {
        room.MostHits = player;
      }
      if (room.MostKills == null) {
        room.MostKills = player;
      }
      if (player.hits > room.MostHits.hits) {
        room.MostHits = player;
      }
      if (player.kills > room.MostKills.kills) {
        room.MostKills = player;
      }
    }
    room.broadcast(GameEndingComposer.compose(room));
  }
}


