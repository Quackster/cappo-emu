package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.engine.ObjectUpdateComposer;
import cappo.protocol.messages.composers.room.engine.PlaceObjectErrorComposer;

public class MoveObjectParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || ((avatar.controllerLevel != 1) && 
      (avatar.controllerLevel < 4)))
    {
      QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11));
      return;
    }
    FloorItem item = avatar.room.getFloorItem(Main.currentPacket.readInt());
    if (item == null)
    {
      QueueWriter.write(Main.socket, PlaceObjectErrorComposer.compose(11));
      return;
    }
    int x = Main.currentPacket.readInt();
    int y = Main.currentPacket.readInt();
    Direction8 rot = Direction8.getDirection(Main.currentPacket.readInt());
    if (!avatar.room.setFloorItem(Main, item, x, y, rot, false)) {
      QueueWriter.write(Main.socket, ObjectUpdateComposer.compose(item));
    }
  }
}


