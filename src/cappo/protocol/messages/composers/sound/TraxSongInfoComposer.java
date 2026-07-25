package cappo.protocol.messages.composers.sound;

import cappo.engine.logging.Log;
import cappo.engine.network.MessageWriter;
import cappo.game.sound.trax.Trax;
import cappo.game.sound.trax.TraxDisc;
import cappo.protocol.messages.Composer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TraxSongInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(List<Integer> Discs)
  {
    int i = 0;
    MessageWriter ClientMessage = new MessageWriter(50 + Discs.size() * 500);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(ClientMessage.setSaved(Integer.valueOf(0)), ClientMessage);
    for (Iterator localIterator = Discs.iterator(); localIterator.hasNext();)
    {
      int DiscId = ((Integer)localIterator.next()).intValue();
      TraxDisc Disc = (TraxDisc)Trax.songDiscs.get(Integer.valueOf(DiscId));
      if (Disc == null)
      {
        Log.printLog("DiscId[" + DiscId + "] is Invalid..");
      }
      else
      {
        Composer.add(Integer.valueOf(Disc.Id), ClientMessage);
        Composer.add("", ClientMessage);
        Composer.add(Disc.Name, ClientMessage);
        Composer.add(Disc.SongData, ClientMessage);
        Composer.add(Integer.valueOf(Disc.Length), ClientMessage);
        Composer.add(Disc.Author, ClientMessage);
        i++;
      }
    }
    ClientMessage.writeSaved(Integer.valueOf(i));
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


