package cappo.protocol.messages.events.navigator;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.roomlisting.RoomListing;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.RoomRatingComposer;
import java.util.List;

public class RateFlatParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    if ((Main.avatarData.ratedRooms.contains(Integer.valueOf(room.roomId))) || (Main.playerData.userId == room.roomData.roomOwner.userId)) {
      return;
    }
    int rating = Main.currentPacket.readInt();
    if (rating == 1) {
      room.roomData.rating += 1;
    } else {
      room.roomData.rating -= 1;
    }
    RoomListing.updateMostScoreRooms(room);
    Main.avatarData.ratedRooms.add(Integer.valueOf(room.roomId));
    QueueWriter.write(Main.socket, RoomRatingComposer.compose(room.roomData.rating, false));
  }
}


