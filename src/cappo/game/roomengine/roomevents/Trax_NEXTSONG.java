package cappo.game.roomengine.roomevents;

import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.sound.trax.TraxDisc;
import cappo.game.sound.trax.TraxPlaylist;
import cappo.protocol.messages.composers.sound.NowPlayingComposer;

public class Trax_NEXTSONG
  extends Event
{
  FloorItem Item;
  
  public Trax_NEXTSONG(FloorItem item)
  {
    this.Item = item;
  }
  
  public void run(RoomTask room)
  {
    if (room.traxPlaylist.Playing)
    {
      room.traxPlaylist.NextSong();
      if (room.traxPlaylist.CurrentSong != null)
      {
        this.Ticks += room.traxPlaylist.CurrentSong.Disc.Length / 500;
        room.traxPlaylist.nextSongTime = (System.currentTimeMillis() + room.traxPlaylist.CurrentSong.Disc.Length);
        room.sendMessage(NowPlayingComposer.compose(room.traxPlaylist, 0));
      }
      else
      {
        room.traxPlaylist.Playing = false;
      }
    }
  }
}


