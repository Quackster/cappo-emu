package cappo.protocol.messages.composers.sound;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.sound.trax.TraxDisc;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class UserSongDisksInventoryComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<SongItem> SongInInventory)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(SongInInventory.size()), ClientMessage);
    for (SongItem disc : SongInInventory)
    {
      Composer.add(Integer.valueOf(disc.itemId), ClientMessage);
      Composer.add(Integer.valueOf(disc.Disc.Id), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


