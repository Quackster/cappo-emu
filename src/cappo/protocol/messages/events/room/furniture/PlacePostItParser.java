package cappo.protocol.messages.events.room.furniture;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.item.wall.RoomWallItemData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.furni.PostItPlacedComposer;
import cappo.protocol.messages.composers.room.engine.PlaceObjectErrorComposer;

public class PlacePostItParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    GenericWallItem item = (GenericWallItem)Main.inventory.getItem(Main.currentPacket.readInt());
    if ((item == null) || (item.baseItem.interactorType != Interactor.InteractorType.postit)) {
      return;
    }
    String location = Main.currentPacket.readString();
    String[] DataBits = location.split(" ");
    if (DataBits.length < 3) {
      return;
    }
    String[] widD = DataBits[0].substring(3).split(",");
    int widthX = Integer.parseInt(widD[0]);
    int widthY = Integer.parseInt(widD[1]);
    if ((widthX < 0) || (widthY < 0) || (widthX > 200) || (widthY > 200))
    {
      QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11));
      return;
    }
    String[] lenD = DataBits[1].substring(2).split(",");
    int lengthX = Integer.parseInt(lenD[0]);
    int lengthY = Integer.parseInt(lenD[1]);
    if ((lengthX < 0) || (lengthY < 0) || (lengthX > 200) || (lengthY > 200))
    {
      QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11)); return;
    }
    char side;
    if (DataBits[2].equals("r")) {
      side = 'r';
    } else {
      side = 'l';
    }
    item.setRoomData(new RoomWallItemData(avatar.room, item, side, widthX, widthY, lengthX, lengthY));
    item.extraData.setExtraData("FFFF33 ");
    
    QueueWriter.write(Main.socket, PostItPlacedComposer.compose(item));
    if (avatar.room.setWallItem(Main, item, true)) {
      Main.inventoryRemoveItem(item.itemId, true);
    }
  }
}
