package cappo.protocol.messages.events.userdefinedroomevents;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.wired.effect.WiredEffectBase;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.userdefinedroomevents.WiredUpdateFailedComposer;
import cappo.protocol.messages.composers.userdefinedroomevents.WiredUpdatedComposer;

public class UpdateActionParser
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
    if (!(Item instanceof WiredEffectBase))
    {
      QueueWriter.write(Main.socket, WiredUpdateFailedComposer.compose("Error"));
      return;
    }
    WiredEffectBase effect = (WiredEffectBase)Item;
    
    int count = Main.currentPacket.readInt();
    if (count > 0) {
      for (int i = 0; i < count; i++) {
        effect.setWiredOption(i, Main.currentPacket.readInt());
      }
    }
    effect.setWiredData(Main.currentPacket.readString());
    
    effect.refreshItems();
    count = Main.currentPacket.readInt();
    for (int i = 0; i < count; i++) {
      effect.addItem(avatar.room.getFloorItem(Main.currentPacket.readInt()));
    }
    effect.cleanDeletedItems();
    
    effect.delayEffect = Main.currentPacket.readInt();
    effect.selectionType = Main.currentPacket.readInt();
    
    QueueWriter.write(Main.socket, WiredUpdatedComposer.compose());
  }
}


