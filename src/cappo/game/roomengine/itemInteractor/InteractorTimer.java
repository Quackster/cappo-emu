package cappo.game.roomengine.itemInteractor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BaseItem.ItemType;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.floor.wired.trigger.WiredTriggerBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.roomevents.Item_TIMER;
import cappo.game.roomengine.wired.WiredManager;
import java.util.Map;

public class InteractorTimer
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem item)
  {
    room.ScorePoints_R = 0;
    room.ScorePoints_G = 0;
    room.ScorePoints_B = 0;
    room.ScorePoints_Y = 0;
    item.setIntData(60);
  }
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem fitem, int Request, boolean UserHasRights)
  {
    if (!UserHasRights) {
      return;
    }
    GenericFloorItem Item = (GenericFloorItem)fitem;
    if (Request == 1)
    {
      if (room.roomData.haveFlag(64))
      {
        room.roomData.setFlag(64, false);
      }
      else
      {
        if (room.ScorePoints_R == -1)
        {
          for (FloorItem Item_ : room.FloorItems.values()) {
            if (Item_.baseItem.interactorType == Interactor.InteractorType.banzaifloor)
            {
              GenericFloorItem item_ = (GenericFloorItem)Item_;
              item_.setIntData(1);
              room.floorItemUpdateNeeded(item_);
            }
            else if (Item_.baseItem.itemType == BaseItem.ItemType.ROOMGAME_GATE)
            {
              room.squareFlag.SetFlag(Item_.getXy(), 4, false);
            }
          }
          WiredTriggerBase.launchTriggers(room.wiredManager.triggersGameStarts, null, null);
        }
        room.roomData.setFlag(64, true);
        room.addItemEvent(new Item_TIMER(Item), 0);
      }
    }
    else
    {
      if (room.roomData.haveFlag(64)) {
        return;
      }
      for (FloorItem Item_ : room.FloorItems.values()) {
        if (Item_.baseItem.interactorType == Interactor.InteractorType.banzaifloor)
        {
          GenericFloorItem item_ = (GenericFloorItem)Item_;
          item_.setIntData(0);
          room.floorItemUpdateNeeded(item_);
        }
        else if (Item_.baseItem.itemType == BaseItem.ItemType.ROOMGAME_GATE)
        {
          room.squareFlag.SetFlag(Item_.getXy(), 4, true);
        }
      }
      room.ScorePoints_R = -1;
      room.ScorePoints_G = -1;
      room.ScorePoints_B = -1;
      room.ScorePoints_Y = -1;
      if ((Item.getIntData() >= 600) || (Item.getIntData() < 1))
      {
        Item.setIntData(60);
      }
      else
      {
        Item.decIntData(Item.getIntData() % 60);
        Item.incIntData(60);
      }
      room.floorItemUpdateNeeded(Item);
    }
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
/* :0:   */ }


