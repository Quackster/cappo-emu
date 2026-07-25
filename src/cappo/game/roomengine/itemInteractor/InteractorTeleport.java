package cappo.game.roomengine.itemInteractor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Teleports;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.roomevents.Teleport_CLOSE;
import cappo.game.roomengine.roomevents.Teleport_IN;

public class InteractorTeleport
  extends Interactor
{
  public class TeleportAttach
  {
    public int itemId;
    public int roomId;
    
    public TeleportAttach(int teleId, int teleportRoom)
    {
      this.itemId = teleId;
      this.roomId = teleportRoom;
    }
  }
  
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item)
  {
    Item.setIntData(0);
  }
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem fItem, int Request, boolean UserHasRights)
  {
    if ((User == null) || (User.teleport != null)) {
      return;
    }
    GenericFloorItem Item = (GenericFloorItem)fItem;
    
    Avatar avatar = User.avatar;
    
    byte[] xy = Item.SquareInFront();
    if (((avatar.x == xy[0]) && (avatar.y == xy[1])) || ((avatar.x == Item.getX()) && (avatar.y == Item.getY())))
    {
      int TeleId = Teleports.getTele(Item.itemId);
      if (TeleId != -1)
      {
        Item.setIntData(2);
        room.floorItemUpdateNeeded(Item);
        avatar.allowOverride = true;
        avatar.moveTo(Item.getX(), Item.getY());
        
        int teleportRoom = Teleports.getRoom(TeleId);
        if (teleportRoom != -1)
        {
          User.teleport = new TeleportAttach(TeleId, teleportRoom);
          room.addUserEvent(new Teleport_IN(avatar), 2);
          room.addUserEvent(new Teleport_CLOSE(avatar, Item), 2);
        }
      }
    }
    else
    {
      avatar.moveTo(xy[0], xy[1]);
    }
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
}


