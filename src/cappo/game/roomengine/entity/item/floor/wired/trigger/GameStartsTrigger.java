package cappo.game.roomengine.entity.item.floor.wired.trigger;

import cappo.game.roomengine.wired.WiredManager;
import java.util.List;

public class GameStartsTrigger
  extends WiredTriggerBase
{
  public int getCode()
  {
    return 8;
  }
  
  public void setManager(WiredManager manager)
  {
    super.setManager(manager);
    this.wiredManager.triggersGameStarts.add(this);
  }
  
  public void removeManager()
  {
    this.wiredManager.triggersGameStarts.remove(this);
    super.removeManager();
  }
}


