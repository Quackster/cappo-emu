package cappo.protocol.messages.composers.room.furniture;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.protocol.messages.Composer;

public class YoutubeDisplayPlaylistsMessageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(GenericFloorItem item)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


