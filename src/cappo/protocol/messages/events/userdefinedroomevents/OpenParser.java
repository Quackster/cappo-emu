package cappo.protocol.messages.events.userdefinedroomevents;

import cappo.engine.network.MessageReader;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BaseItem.ItemType;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.wired.WiredItemBase;
import cappo.game.roomengine.entity.item.floor.wired.effect.WiredEffectBase;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.IncomingMessageEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OpenParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    FloorItem item = avatar.room.getFloorItem(Main.currentPacket.readInt());
    if (item == null) {
      return;
    }
    if (!(item instanceof WiredItemBase)) {
      return;
    }
    WiredItemBase wired = (WiredItemBase)item;
    
    MessageWriter ClientMessage = new MessageWriter();
    if (item.baseItem.itemType == BaseItem.ItemType.WIRED_TRIGGER)
    {
      Composer.initPacket(970, ClientMessage);
      serializeWired(wired, ClientMessage);
      Composer.add(Integer.valueOf(wired.getCode()), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      
      Composer.endPacket(ClientMessage);
      QueueWriter.write(Main.socket, ClientMessage);
    }
    else if (item.baseItem.itemType == BaseItem.ItemType.WIRED_EFFECT)
    {
      Composer.initPacket(2221, ClientMessage);
      serializeWired(wired, ClientMessage);
      Composer.add(Integer.valueOf(wired.getCode()), ClientMessage);
      Composer.add(Integer.valueOf(((WiredEffectBase)wired).delayEffect), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      
      Composer.endPacket(ClientMessage);
      QueueWriter.write(Main.socket, ClientMessage);
    }
    else if (item.baseItem.itemType == BaseItem.ItemType.WIRED_CONDITION)
    {
      Composer.initPacket(3402, ClientMessage);
      serializeWired(wired, ClientMessage);
      Composer.add(Integer.valueOf(wired.getCode()), ClientMessage);
      Composer.endPacket(ClientMessage);
      QueueWriter.write(Main.socket, ClientMessage);
    }
  }
  
  private void serializeWired(WiredItemBase wired, MessageWriter ClientMessage)
  {
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(5), ClientMessage);
    Composer.add(Integer.valueOf(wired.items.size()), ClientMessage);
    for (Iterator localIterator = wired.items.keySet().iterator(); localIterator.hasNext();)
    {
      int id = ((Integer)localIterator.next()).intValue();
      Composer.add(Integer.valueOf(id), ClientMessage);
    }
    Composer.add(Integer.valueOf(wired.baseItem.SpriteId), ClientMessage);
    Composer.add(Integer.valueOf(wired.itemId), ClientMessage);
    Composer.add(wired.getWiredData(), ClientMessage);
    int[] options = wired.getWiredOptions();
    Composer.add(Integer.valueOf(options.length), ClientMessage);
    for (int option : options) {
      Composer.add(Integer.valueOf(option), ClientMessage);
    }
    Composer.add(Integer.valueOf(wired.selectionType), ClientMessage);
  }
}


