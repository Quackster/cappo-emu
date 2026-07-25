package cappo.game.roomengine.entity.item.floor.wired.condition;

import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.item.floor.wired.WiredItemBase;

public abstract class WiredConditionBase
  extends WiredItemBase
{
  public abstract boolean checkCondition(Connection paramConnection);
}


