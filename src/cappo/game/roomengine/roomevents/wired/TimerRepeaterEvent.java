package cappo.game.roomengine.roomevents.wired;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.wired.trigger.RepeatTrigger;
import cappo.game.roomengine.roomevents.Event;

public class TimerRepeaterEvent
  extends Event
{
  private final RepeatTrigger wired;
  private final Connection invoker;
  
  public TimerRepeaterEvent(RepeatTrigger item, Connection ivk)
  {
    this.wired = item;
    this.invoker = ivk;
  }
  
  public boolean equals(Object arg0)
  {
    if (super.equals(arg0))
    {
      TimerRepeaterEvent comp = (TimerRepeaterEvent)arg0;
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
    this.Ticks = this.wired.delay;
    RepeatTrigger.doTrigger(this.wired, this.invoker);
  }
}


