package cappo.protocol.messages.events.recycler;

import cappo.engine.network.MessageReader;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.collections.BaseItem;
import cappo.game.collections.Utils;
import cappo.game.player.data.AvatarData;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.item.Item;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.IncomingMessageEvent;

public class RecycleItemsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (Utils.getTimestamp() < Main.avatarData.EcotronNextTime) {
      return;
    }
    int count = Main.currentPacket.readInt();
    if (count != 5) {
      return;
    }
    for (int i = 0; i < count; i++)
    {
      Item Item = Main.inventory.getFurni(Main.currentPacket.readInt());
      if ((Item == null) || (!Item.baseItem.AllowRecycle)) {
        return;
      }
      Main.inventoryRemoveItem(Item.itemId, Item.baseItem.Type.equals("i"));
    }
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(508, ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    QueueWriter.write(Main.socket, ClientMessage);
    
    Main.avatarData.EcotronNextTime = (Utils.getTimestamp() + 300L);
  }
}


