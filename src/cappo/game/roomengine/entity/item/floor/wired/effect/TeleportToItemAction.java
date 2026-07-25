package cappo.game.roomengine.entity.item.floor.wired.effect;

import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.roomevents.wired.TeleportToEvent;
import java.util.Map;
import java.util.Set;

public class TeleportToItemAction
  extends WiredEffectBase
{
  public int getCode()
  {
    return 0;
  }
  
  public boolean needUser()
  {
    return true;
  }
  
  public void invoke(Connection invoker)
  {
    if (this.delayEffect > 0) {
      getRoom().addItemEvent(new TeleportToEvent(this, invoker), this.delayEffect);
    } else {
      doEffect(this, invoker);
    }
  }
  
  public static void doEffect(TeleportToItemAction wired, Connection invoker)
  {
    if (!wired.items.isEmpty())
    {
      Object[] keys = wired.items.keySet().toArray();
      Integer randKey = (Integer)keys[cappo.game.collections.Utils.GetRandomNumber(0, keys.length - 1)];
      FloorItem item = (FloorItem)wired.items.get(randKey);
      if (item == null) {
        return;
      }
      RoomTask room = item.getRoom();
      if (room == null) {
        return;
      }
      Avatar avatar = invoker.avatar;
      
      avatar.clearMovement();
      if (item.getRoom().model != null)
      {
        avatar.x = item.getX();
        avatar.y = item.getY();
        int xy = item.getX() + item.getY() * item.getRoom().model.widthX;
        
        room.entityWalk(xy, avatar, true);
        room.entityWalk(avatar.xy, avatar, false);
        avatar.xy = xy;
      }
      else
      {
        Log.printLog("Critic: TeleportTo NULL OBJECT");
        return;
      }
      avatar.z = item.getZ();
      wired.getRoom().updateUserStatus(avatar, true);
      wired.getRoom().userUpdateNeeded(avatar);
    }
  }
}


