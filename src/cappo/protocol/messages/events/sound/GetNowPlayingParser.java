package cappo.protocol.messages.events.sound;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.sound.trax.TraxPlaylist;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.sound.NowPlayingComposer;

public class GetNowPlayingParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    if (room.traxPlaylist.Playing) {
      QueueWriter.write(Main.socket, NowPlayingComposer.compose(room.traxPlaylist, (int)(room.traxPlaylist.nextSongTime - System.currentTimeMillis())));
    }
  }
}


