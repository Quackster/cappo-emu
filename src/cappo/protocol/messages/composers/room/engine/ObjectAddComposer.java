package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeFloorItem;

public class ObjectAddComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(FloorItem Item)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    SerializeFloorItem.parse(ClientMessage, Item);
    Composer.add(Item.owner.userName, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


