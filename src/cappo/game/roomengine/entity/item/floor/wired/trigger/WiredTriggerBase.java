package cappo.game.roomengine.entity.item.floor.wired.trigger;

import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.item.floor.wired.WiredItemBase;
import cappo.game.roomengine.entity.item.floor.wired.condition.WiredConditionBase;
import cappo.game.roomengine.entity.item.floor.wired.effect.WiredEffectBase;
import cappo.game.roomengine.wired.WiredManager;
import java.util.List;
import java.util.Map;

public abstract class WiredTriggerBase
  extends WiredItemBase
{
  public boolean needUser()
  {
    return false;
  }
  
  public static boolean launchTriggers(List<WiredTriggerBase> triggers, Connection cn, Object extra)
  {
    boolean launched = false;
    for (WiredTriggerBase trigger : triggers) {
      launched = (trigger.launch(cn, extra)) || (launched);
    }
    return launched;
  }
  
  public boolean launch(Connection cn, Object extra)
  {
    if ((this.wiredManager == null) || (getRoom() == null)) {
      return true;
    }
    Map<Integer, WiredEffectBase> effects = (Map)this.wiredManager.wiredEffectsMap.get(Integer.valueOf(getXy()));
    if (effects == null) {
      return true;
    }
    Map<Integer, WiredConditionBase> conditions = (Map)this.wiredManager.wiredConditionsMap.get(Integer.valueOf(getXy()));
    if (conditions != null) {
      for (WiredConditionBase condition : conditions.values()) {
        if (((cn != null) || (!condition.needUser())) && 
          (!condition.checkCondition(cn))) {
          return true;
        }
      }
    }
    for (WiredEffectBase effect : effects.values()) {
      if ((cn != null) || (!effect.needUser())) {
        effect.invoke(cn);
      }
    }
    return true;
  }
}


