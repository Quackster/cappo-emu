package cappo.game.roomengine.roomevents;

import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;

public class HabboWheel_RUN
  extends Event
{
  GenericWallItem Item;
  
  public HabboWheel_RUN(GenericWallItem item)
  {
    this.Item = item;
  }
  
  public void run(RoomTask room)
  {
    this.Item.extraData.setExtraData(Integer.toString(Utils.GetRandomNumber(1, 10)));
    room.wallItemUpdateNeeded(this.Item);
  }
}


