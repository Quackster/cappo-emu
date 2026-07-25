package cappo.game.roomengine.roomevents;
import cappo.game.roomengine.itemInteractor.InteractorTeleport;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Teleports;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.InteractorTeleport.TeleportAttach;

public class Teleport_IN
  extends Event
{
  Avatar User;
  
  public Teleport_IN(Avatar user)
  {
    this.User = user;
  }
  
  public void run(RoomTask room)
  {
    this.User.allowOverride = false;
    InteractorTeleport.TeleportAttach teleport = this.User.cn.teleport;
    
    int TeleRoomId = Teleports.getRoom(teleport.itemId);
    if (TeleRoomId == -1) {
      return;
    }
    if (room.roomId == TeleRoomId)
    {
      GenericFloorItem Item = (GenericFloorItem)room.getFloorItem(teleport.itemId);
      if (Item != null)
      {
        Item.setIntData(2);
        room.floorItemUpdateNeeded(Item);
        this.User.SetPos(Item.getX(), Item.getY(), Item.getZ());
        this.User.SetRot(Item.getDir());
        room.addUserEvent(new Teleport_OUT(this.User, Item), 2);
      }
    }
    else
    {
      this.User.cn.loadRoom(TeleRoomId, "");
    }
  }
}
