package cappo.game.roomengine.roomevents.wired;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.wired.effect.TeleportToItemAction;
import cappo.game.roomengine.roomevents.Event;

public class TeleportToEvent
  extends Event
{
  private final TeleportToItemAction wired;
  private final Connection invoker;
  
  public TeleportToEvent(TeleportToItemAction item, Connection ivk)
  {
    this.wired = item;
    this.invoker = ivk;
  }
  
  public boolean equals(Object arg0)
  {
    if (super.equals(arg0))
    {
      TeleportToEvent comp = (TeleportToEvent)arg0;
      if (comp.invoker.playerData.userId == this.invoker.playerData.userId) {
        return true;
      }
    }
    return false;
  }
  
  public void run(RoomTask room)
  {
    TeleportToItemAction.doEffect(this.wired, this.invoker);
  }
}


