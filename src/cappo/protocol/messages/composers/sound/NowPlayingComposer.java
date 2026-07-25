package cappo.protocol.messages.composers.sound;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.sound.trax.TraxDisc;
import cappo.game.sound.trax.TraxPlaylist;
import cappo.protocol.messages.Composer;

public class NowPlayingComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(TraxPlaylist list, int timeForNextSong)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(list.CurrentSong.Disc.Id), ClientMessage);
    Composer.add(Integer.valueOf(list.SongIndex), ClientMessage);
    Composer.add(Integer.valueOf(list.CurrentSong.Disc.Id), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(timeForNextSong), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


