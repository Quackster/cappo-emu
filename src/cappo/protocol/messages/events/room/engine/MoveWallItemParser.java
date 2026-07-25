package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.item.wall.RoomWallItemData;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.engine.PlaceObjectErrorComposer;

public class MoveWallItemParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || ((avatar.controllerLevel != 1) && 
      (avatar.controllerLevel < 4))) {
      return;
    }
    WallItem wallItem = avatar.room.getWallItem(Main.currentPacket.readInt());
    if (wallItem == null) {
      return;
    }
    if (!(wallItem instanceof GenericWallItem)) {
      return;
    }
    String[] DataBits = Main.currentPacket.readString().split(" ");
    
    String[] widD = DataBits[0].substring(3).split(",");
    int widthX = Integer.parseInt(widD[0]);
    int widthY = Integer.parseInt(widD[1]);
    if ((widthX < 0) || (widthY < 0) || (widthX > 100) || (widthY > 100))
    {
      QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11));
      return;
    }
    String[] lenD = DataBits[1].substring(2).split(",");
    int lengthX = Integer.parseInt(lenD[0]);
    int lengthY = Integer.parseInt(lenD[1]);
    if ((lengthX < 0) || (lengthY < 0) || (lengthX > 100) || (lengthY > 100))
    {
      QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11)); return;
    }
    char side;
    if (DataBits[2].equals("r")) {
      side = 'r';
    } else {
      side = 'l';
    }
    wallItem.setRoomData(new RoomWallItemData(avatar.room, wallItem, side, widthX, widthY, lengthX, lengthY));
    
    avatar.room.setWallItem(Main, (GenericWallItem)wallItem, false);
  }
}


