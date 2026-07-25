package cappo.game.roomengine.roomevents.wired;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.wired.effect.GiveReward;
import cappo.game.roomengine.roomevents.Event;

public class GiveRewardEvent
  extends Event
{
  private final GiveReward wired;
  private final Connection invoker;
  
  public GiveRewardEvent(GiveReward item, Connection ivk)
  {
    this.wired = item;
    this.invoker = ivk;
  }
  
  public boolean equals(Object arg0)
  {
    if (super.equals(arg0))
    {
      GiveRewardEvent comp = (GiveRewardEvent)arg0;
      if (this.invoker == null) {
        return comp.invoker == null;
      }
      return (comp.invoker != null) && (comp.invoker.playerData.userId == this.invoker.playerData.userId);
    }
    return false;
  }
  
  public void run(RoomTask room)
  {
    GiveReward.doEffect(this.wired, this.invoker);
  }
}


