package cappo.game.games.snowwar.tasks;

import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.composers.games.snowwar.StageStartingComposer;

public class SnowStageStarting
{
  public static void exec(SnowWarRoom room)
  {
    room.broadcast(StageStartingComposer.compose(room));
  }
}