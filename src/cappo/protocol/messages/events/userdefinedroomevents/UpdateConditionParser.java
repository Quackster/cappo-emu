package cappo.protocol.messages.events.userdefinedroomevents;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.wired.condition.WiredConditionBase;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.userdefinedroomevents.WiredUpdateFailedComposer;
import cappo.protocol.messages.composers.userdefinedroomevents.WiredUpdatedComposer;

public class UpdateConditionParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    FloorItem Item = avatar.room.getFloorItem(Main.currentPacket.readInt());
    if (Item == null)
    {
      QueueWriter.write(Main.socket, WiredUpdateFailedComposer.compose("Error"));
      return;
    }
    if (!(Item instanceof WiredConditionBase))
    {
      QueueWriter.write(Main.socket, WiredUpdateFailedComposer.compose("Error"));
      return;
    }
    WiredConditionBase condition = (WiredConditionBase)Item;
    
    int count = Main.currentPacket.readInt();
    if (count > 0) {
      for (int i = 0; i < count; i++) {
        condition.setWiredOption(i, Main.currentPacket.readInt());
      }
    }
    condition.setWiredData(Main.currentPacket.readString());
    
    condition.refreshItems();
    count = Main.currentPacket.readInt();
    for (int i = 0; i < count; i++) {
      condition.addItem(avatar.room.getFloorItem(Main.currentPacket.readInt()));
    }
    condition.cleanDeletedItems();
    
    condition.selectionType = Main.currentPacket.readInt();
    
    QueueWriter.write(Main.socket, WiredUpdatedComposer.compose());
  }
}


