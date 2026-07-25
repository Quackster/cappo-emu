package cappo.game.roomengine.entity.item;

import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;

public abstract class RoomItemData
{
  public BaseItem baseItem;
  public RoomTask currentRoom;
  
  public int getRoomId()
  {
    return this.currentRoom.roomId;
  }
  
  public RoomItemData(BaseItem base, RoomTask room)
  {
    this.baseItem = base;
    this.currentRoom = room;
  }
}


