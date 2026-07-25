package cappo.game.roomengine.itemInteractor;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.floor.wired.WiredItemBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.wired.WiredManager;
import cappo.protocol.messages.composers.userdefinedroomevents.OpenWiredComposer;

public class InteractorWiredFurnis
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item)
  {
    if ((Item instanceof WiredItemBase)) {
      room.wiredManager.registerWired((WiredItemBase)Item, Item.baseItem.itemType);
    }
  }
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item)
  {
    if ((Item instanceof WiredItemBase)) {
      room.wiredManager.removeWired((WiredItemBase)Item, Item.baseItem.itemType, Item.getXy());
    }
  }
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem fitem, int Request, boolean UserHasRights)
  {
    if (User == null) {
      return;
    }
    if (!UserHasRights) {
      return;
    }
    GenericFloorItem Item = (GenericFloorItem)fitem;
    
    Item.setIntData(Item.getIntData() == 0 ? 1 : 0);
    room.floorItemUpdateNeeded(Item);
    
    QueueWriter.writeAndFlush(User.socket, OpenWiredComposer.compose(Item.itemId));
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
}


