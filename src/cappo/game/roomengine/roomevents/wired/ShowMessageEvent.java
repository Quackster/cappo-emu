package cappo.game.roomengine.roomevents.wired;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.wired.effect.ShowMessageAction;
import cappo.game.roomengine.roomevents.Event;

public class ShowMessageEvent
  extends Event
{
  private final ShowMessageAction wired;
  private final Connection invoker;
  
  public ShowMessageEvent(ShowMessageAction item, Connection ivk)
  {
    this.wired = item;
    this.invoker = ivk;
  }
  
  public boolean equals(Object arg0)
  {
    if (super.equals(arg0))
    {
      ShowMessageEvent comp = (ShowMessageEvent)arg0;
      if (comp.invoker.playerData.userId == this.invoker.playerData.userId) {
        return true;
      }
    }
    return false;
  }
  
  public void run(RoomTask room)
  {
    ShowMessageAction.doEffect(this.wired, this.invoker);
  }
}


