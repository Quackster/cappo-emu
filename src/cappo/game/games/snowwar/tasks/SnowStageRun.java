package cappo.game.games.snowwar.tasks;

import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.composers.games.snowwar.StageRunningComposer;

public class SnowStageRun
{
  public static void exec(SnowWarRoom room)
  {
    room.broadcast(StageRunningComposer.compose(120));
  }
}


