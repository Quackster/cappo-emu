package cappo.game.sound.trax;

import cappo.game.roomengine.entity.item.floor.SongItem;
import java.util.ArrayList;
import java.util.List;

public class TraxPlaylist
{
  public SongItem CurrentSong;
  public boolean Playing;
  public List<SongItem> PlaylistByIndex = new ArrayList();
  public int SongIndex;
  public long nextSongTime;
  
  public void NextSong()
  {
    this.SongIndex = (++this.SongIndex % this.PlaylistByIndex.size());
    this.CurrentSong = ((SongItem)this.PlaylistByIndex.get(this.SongIndex));
    if (this.CurrentSong == null) {
      this.Playing = false;
    }
  }
  
  public void StartPlaying()
  {
    if (this.PlaylistByIndex.isEmpty()) {
      this.CurrentSong = null;
    } else {
      this.CurrentSong = ((SongItem)this.PlaylistByIndex.get(this.SongIndex = 0));
    }
  }
}


