package cappo.protocol.messages.events.room.furniture;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.MoodlightData;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class RoomDimmerChangeStateParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if (avatar.controllerLevel < 4) {
      return;
    }
    RoomTask room = avatar.room;
    if (room.MoodlightData == null) {
      return;
    }
    room.MoodlightData.Enabled = (!room.MoodlightData.Enabled);
    
    GenericWallItem Item = (GenericWallItem)room.getWallItem(room.MoodlightData.ItemId);
    if (Item == null) {
      return;
    }
    Item.extraData.setExtraData(room.MoodlightData.GenerateExtraData());
    room.wallItemUpdateNeeded(Item);
  }
}


