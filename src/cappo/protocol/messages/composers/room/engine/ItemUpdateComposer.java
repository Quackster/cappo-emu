package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeWallItem;

public class ItemUpdateComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(GenericWallItem item)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    SerializeWallItem.parse(ClientMessage, item);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


