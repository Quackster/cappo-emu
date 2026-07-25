package cappo.game.roomengine.wired;
import cappo.game.collections.BaseItem;

import cappo.engine.player.Connection;
import cappo.game.collections.BaseItem.ItemType;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.wired.WiredItemBase;
import cappo.game.roomengine.entity.item.floor.wired.condition.WiredConditionBase;
import cappo.game.roomengine.entity.item.floor.wired.effect.WiredEffectBase;
import cappo.game.roomengine.entity.item.floor.wired.trigger.WiredTriggerBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WiredManager
{
  public static final int MAX_CHILDS_PER_WIRED = 5;
  private static final int MAX_WIREDS_PER_ROOM = 100;
  private int count;
  public final List<WiredTriggerBase> triggersEntersRoom = new ArrayList();
  public final List<WiredTriggerBase> triggersTimers = new ArrayList();
  public final List<WiredTriggerBase> triggersGameEnds = new ArrayList();
  public final List<WiredTriggerBase> triggersGameStarts = new ArrayList();
  public final List<WiredTriggerBase> triggersUserSays = new ArrayList();
  public final Map<Integer, List<WiredTriggerBase>> triggersSateChanged = new ConcurrentHashMap();
  public final Map<Integer, List<WiredTriggerBase>> triggersWalksOffFurni = new ConcurrentHashMap();
  public final Map<Integer, List<WiredTriggerBase>> triggersWalksOnFurni = new ConcurrentHashMap();
  public final Map<Integer, Map<Integer, WiredConditionBase>> wiredConditionsMap = new ConcurrentHashMap();
  public final Map<Integer, Map<Integer, WiredEffectBase>> wiredEffectsMap = new ConcurrentHashMap();
  
  public void parseWiredMutacion(Connection User, FloorItem item)
  {
    List<WiredTriggerBase> triggerList = (List)this.triggersSateChanged.get(Integer.valueOf(item.itemId));
    if (triggerList != null) {
      WiredTriggerBase.launchTriggers(triggerList, User, null);
    }
  }
  
  public void parseWiredWalksOffFurni(Connection User, int xy)
  {
    List<WiredTriggerBase> triggerList = (List)this.triggersWalksOffFurni.get(Integer.valueOf(xy));
    if (triggerList != null) {
      WiredTriggerBase.launchTriggers(triggerList, User, null);
    }
  }
  
  public void parseWiredWalksOnFurni(Connection User, int xy)
  {
    List<WiredTriggerBase> triggerList = (List)this.triggersWalksOnFurni.get(Integer.valueOf(xy));
    if (triggerList != null) {
      WiredTriggerBase.launchTriggers(triggerList, User, null);
    }
  }
  
  public void registerWired(WiredItemBase item, BaseItem.ItemType type)
  {
    if (this.count >= 100) {
      return;
    }
    if (type == BaseItem.ItemType.WIRED_EFFECT)
    {
      Map<Integer, WiredEffectBase> effects = (Map)this.wiredEffectsMap.get(Integer.valueOf(item.getXy()));
      if (effects == null)
      {
        effects = new ConcurrentHashMap();
        this.wiredEffectsMap.put(Integer.valueOf(item.getXy()), effects);
      }
      effects.put(Integer.valueOf(item.itemId), (WiredEffectBase)item);
    }
    else if (type == BaseItem.ItemType.WIRED_CONDITION)
    {
      Map<Integer, WiredConditionBase> conditions = (Map)this.wiredConditionsMap.get(Integer.valueOf(item.getXy()));
      if (conditions == null)
      {
        conditions = new ConcurrentHashMap();
        this.wiredConditionsMap.put(Integer.valueOf(item.getXy()), conditions);
      }
      conditions.put(Integer.valueOf(item.itemId), (WiredConditionBase)item);
    }
    item.setManager(this);
    
    this.count += 1;
  }
  
  public void removeWired(WiredItemBase item, BaseItem.ItemType type, int prevXY)
  {
    item.removeAllItems();
    if (type == BaseItem.ItemType.WIRED_EFFECT)
    {
      Map<Integer, WiredEffectBase> effects = (Map)this.wiredEffectsMap.get(Integer.valueOf(prevXY));
      effects.remove(Integer.valueOf(item.itemId));
      if (effects.isEmpty()) {
        this.wiredEffectsMap.remove(Integer.valueOf(item.getXy()));
      }
    }
    else if (type == BaseItem.ItemType.WIRED_CONDITION)
    {
      Map<Integer, WiredConditionBase> conditions = (Map)this.wiredConditionsMap.get(Integer.valueOf(prevXY));
      conditions.remove(Integer.valueOf(item.itemId));
      if (conditions.isEmpty()) {
        this.wiredConditionsMap.remove(Integer.valueOf(item.getXy()));
      }
    }
    if (item.wiredManager != null) {
      item.removeManager();
    }
    this.count -= 1;
  }
  
  public void moveWired(WiredItemBase item, BaseItem.ItemType type, int prevXY)
  {
    if (type == BaseItem.ItemType.WIRED_EFFECT)
    {
      Map<Integer, WiredEffectBase> effects = (Map)this.wiredEffectsMap.get(Integer.valueOf(prevXY));
      effects.remove(Integer.valueOf(item.itemId));
      if (effects.isEmpty()) {
        this.wiredEffectsMap.remove(Integer.valueOf(prevXY));
      }
      effects = (Map)this.wiredEffectsMap.get(Integer.valueOf(item.getXy()));
      if (effects == null)
      {
        effects = new ConcurrentHashMap();
        this.wiredEffectsMap.put(Integer.valueOf(item.getXy()), effects);
      }
      effects.put(Integer.valueOf(item.itemId), (WiredEffectBase)item);
    }
    else if (type == BaseItem.ItemType.WIRED_CONDITION)
    {
      Map<Integer, WiredConditionBase> conditions = (Map)this.wiredConditionsMap.get(Integer.valueOf(prevXY));
      conditions.remove(Integer.valueOf(item.itemId));
      if (conditions.isEmpty()) {
        this.wiredConditionsMap.remove(Integer.valueOf(prevXY));
      }
      conditions = (Map)this.wiredConditionsMap.get(Integer.valueOf(item.getXy()));
      if (conditions == null)
      {
        conditions = new ConcurrentHashMap();
        this.wiredConditionsMap.put(Integer.valueOf(item.getXy()), conditions);
      }
      conditions.put(Integer.valueOf(item.itemId), (WiredConditionBase)item);
    }
  }
}
