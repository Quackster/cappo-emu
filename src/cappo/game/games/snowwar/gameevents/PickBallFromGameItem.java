package cappo.game.games.snowwar.gameevents;

import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.games.snowwar.gameobjects.PickBallsGameItemObject;

public class PickBallFromGameItem
  extends Event
{
  public HumanGameObject player;
  public PickBallsGameItemObject gameItem;
  
  public PickBallFromGameItem(HumanGameObject player, PickBallsGameItemObject gameItem)
  {
    this.EventType = 12;
    this.player = player;
    this.gameItem = gameItem;
  }
  
  public void apply()
  {
    int local1 = this.player.availableSnowBallSlots();
    if (local1 > 0)
    {
      int local2 = this.gameItem.pickUp(1);
      if (local2 > 0) {
        this.player.addSnowBalls(local2);
      }
    }
    if (this.gameItem.concurrentUses > 0) {
      this.gameItem.concurrentUses -= 1;
    }
  }
}


