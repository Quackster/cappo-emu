package cappo.protocol.messages.events.room.furniture;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.purse.CreditBalanceComposer;

public class CreditFurniRedeemParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    RoomTask room = avatar.room;
    
    GenericFloorItem floorItem = (GenericFloorItem)room.getFloorItem(Main.currentPacket.readInt());
    if (floorItem == null) {
      return;
    }
    room.removeFloorItem(floorItem, Main.playerData.userId);
    floorItem.setMysqlState(4);
    
    Main.credits += floorItem.getExtraParam();
    QueueWriter.write(Main.socket, CreditBalanceComposer.compose(Main.credits));
  }
}


