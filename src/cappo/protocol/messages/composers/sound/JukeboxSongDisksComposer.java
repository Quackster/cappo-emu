package cappo.protocol.messages.composers.sound;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.sound.trax.TraxDisc;
import cappo.protocol.messages.Composer;
import java.util.List;

public class JukeboxSongDisksComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(List<SongItem> PlaylistByIndex)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(PlaylistByIndex.size()), ClientMessage);
    int count = 0;
    for (SongItem Song : PlaylistByIndex)
    {
      Composer.add(Integer.valueOf(count++), ClientMessage);
      Composer.add(Integer.valueOf(Song.Disc.Id), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


