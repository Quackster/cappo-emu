package cappo.game.games.snowwar.tasks;

import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.composers.games.snowwar.StageStillLoadingComposer;
import java.util.Collection;
import java.util.Map;

public class SnowStageLoading
{
  public static void exec(SnowWarRoom room)
  {
    Collection<HumanGameObject> playersLoaded = room.getStageLoadedPlayers();
    if (playersLoaded != null)
    {
      room.broadcast(StageStillLoadingComposer.compose(playersLoaded));
      if (!playersLoaded.isEmpty()) {
        return;
      }
    }
    for (HumanGameObject player : room.players.values()) {
      if (!player.stageLoaded) {
        return;
      }
    }
    room.STATUS = 3;
  }
}


