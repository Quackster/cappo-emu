package cappo.game.roomengine.entity.item.floor.wired.trigger;

import cappo.game.roomengine.wired.WiredManager;
import java.util.List;

public class GameEndsTrigger
  extends WiredTriggerBase
{
  public int getCode()
  {
    return 9;
  }
  
  public void setManager(WiredManager manager)
  {
    super.setManager(manager);
    this.wiredManager.triggersGameEnds.add(this);
  }
  
  public void removeManager()
  {
    this.wiredManager.triggersGameEnds.remove(this);
    super.removeManager();
  }
}


