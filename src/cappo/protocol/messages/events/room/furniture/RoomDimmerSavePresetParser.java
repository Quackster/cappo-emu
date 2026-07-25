package cappo.protocol.messages.events.room.furniture;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.MoodlightData;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class RoomDimmerSavePresetParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    RoomTask room = avatar.room;
    if (room.MoodlightData == null) {
      return;
    }
    int Preset = Main.currentPacket.readInt();
    int BackgroundMode = Main.currentPacket.readInt();
    String ColorCode = Main.currentPacket.readString();
    int Intensity = Main.currentPacket.readInt();
    
    room.MoodlightData.Enabled = true;
    room.MoodlightData.CurrentPreset = Preset;
    room.MoodlightData.UpdatePreset(ColorCode, Intensity, BackgroundMode > 1);
    
    GenericWallItem Item = (GenericWallItem)room.getWallItem(room.MoodlightData.ItemId);
    
    Item.extraData.setExtraData(room.MoodlightData.GenerateExtraData());
    room.wallItemUpdateNeeded(Item);
  }
}


