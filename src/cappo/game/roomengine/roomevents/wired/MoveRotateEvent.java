package cappo.game.roomengine.roomevents.wired;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.wired.effect.MoveRotateItemAction;
import cappo.game.roomengine.roomevents.Event;

public class MoveRotateEvent
  extends Event
{
  private final MoveRotateItemAction wired;
  private final Connection invoker;
  
  public MoveRotateEvent(MoveRotateItemAction item, Connection ivk)
  {
    this.wired = item;
    this.invoker = ivk;
  }
  
  public boolean equals(Object arg0)
  {
    if (super.equals(arg0))
    {
      MoveRotateEvent comp = (MoveRotateEvent)arg0;
      if (this.invoker == null) {
        return comp.invoker == null;
      }
      return (comp.invoker != null) && (comp.invoker.playerData.userId == this.invoker.playerData.userId);
    }
    return false;
  }
  
  public void run(RoomTask room)
  {
    MoveRotateItemAction.doEffect(this.wired);
  }
}


