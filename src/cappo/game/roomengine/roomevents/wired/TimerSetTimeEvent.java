package cappo.game.roomengine.roomevents.wired;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.wired.trigger.TimerResetTrigger;
import cappo.game.roomengine.roomevents.Event;

public class TimerSetTimeEvent
  extends Event
{
  private final TimerResetTrigger wired;
  private final Connection invoker;
  
  public TimerSetTimeEvent(TimerResetTrigger item, Connection ivk)
  {
    this.wired = item;
    this.invoker = ivk;
  }
  
  public boolean equals(Object arg0)
  {
    if (super.equals(arg0))
    {
      TimerSetTimeEvent comp = (TimerSetTimeEvent)arg0;
      if (this.invoker == null) {
        return comp.invoker == null;
      }
      return (comp.invoker != null) && (comp.invoker.playerData.userId == this.invoker.playerData.userId);
    }
    return false;
  }
  
  public void run(RoomTask room)
  {
    if ((this.wired.getRoom() != room) || (this.wired.wiredManager == null)) {
      return;
    }
    TimerResetTrigger.doTrigger(this.wired, this.invoker);
  }
}


