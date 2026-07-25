package cappo.protocol.messages.events.sound;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.sound.trax.TraxPlaylist;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.sound.JukeboxSongDisksComposer;

public class GetJukeboxPlayListParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    QueueWriter.write(Main.socket, JukeboxSongDisksComposer.compose(avatar.room.traxPlaylist.PlaylistByIndex));
  }
}


