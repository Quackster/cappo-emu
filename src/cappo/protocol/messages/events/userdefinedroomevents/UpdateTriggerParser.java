package cappo.protocol.messages.events.userdefinedroomevents;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.wired.trigger.WiredTriggerBase;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.userdefinedroomevents.WiredUpdateFailedComposer;
import cappo.protocol.messages.composers.userdefinedroomevents.WiredUpdatedComposer;

public class UpdateTriggerParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null)
    {
      QueueWriter.write(Main.socket, WiredUpdateFailedComposer.compose("Error"));
      return;
    }
    FloorItem Item = avatar.room.getFloorItem(Main.currentPacket.readInt());
    if (Item == null)
    {
      QueueWriter.write(Main.socket, WiredUpdateFailedComposer.compose("Error"));
      return;
    }
    if (!(Item instanceof WiredTriggerBase))
    {
      QueueWriter.write(Main.socket, WiredUpdateFailedComposer.compose("Error"));
      return;
    }
    WiredTriggerBase trigger = (WiredTriggerBase)Item;
    
    int count = Main.currentPacket.readInt();
    if (count > 0) {
      for (int i = 0; i < count; i++) {
        trigger.setWiredOption(i, Main.currentPacket.readInt());
      }
    }
    trigger.setWiredData(Main.currentPacket.readString());
    
    trigger.refreshItems();
    count = Main.currentPacket.readInt();
    for (int i = 0; i < count; i++) {
      trigger.addItem(avatar.room.getFloorItem(Main.currentPacket.readInt()));
    }
    trigger.cleanDeletedItems();
    
    trigger.selectionType = Main.currentPacket.readInt();
    
    QueueWriter.write(Main.socket, WiredUpdatedComposer.compose());
  }
}


