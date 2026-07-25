package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeWallItem;

public class ItemAddComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(GenericWallItem Item)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    SerializeWallItem.parse(ClientMessage, Item);
    Composer.add(Item.owner.userName, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


