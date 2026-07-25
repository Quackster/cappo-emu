package cappo.game.roomengine.entity.item.floor.wired.effect;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.roomevents.wired.ToggleItemStateEvent;
import java.util.Map;

public class ToggleItemStateAction
  extends WiredEffectBase
{
  public int getCode()
  {
    return 8;
  }
  
  public boolean needUser()
  {
    return false;
  }
  
  public void invoke(Connection invoker)
  {
    if (this.delayEffect > 0) {
      getRoom().addItemEvent(new ToggleItemStateEvent(this, invoker), this.delayEffect);
    } else {
      doEffect(this, invoker);
    }
  }
  
  public static void doEffect(ToggleItemStateAction wired, Connection invoker)
  {
    if (!wired.items.isEmpty()) {
      for (FloorItem item : wired.items.values()) {
        item.baseItem.interactor.OnTriggerFloor(item.getRoom(), invoker, item, 0, true);
      }
    }
  }
}


