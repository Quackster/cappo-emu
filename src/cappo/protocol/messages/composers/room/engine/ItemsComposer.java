package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeWallItem;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<GenericWallItem> WallItems)
  {
    Map<Integer, String> owners = new HashMap();
    for (WallItem Item : WallItems) {
      if (!owners.containsKey(Integer.valueOf(Item.owner.userId))) {
        owners.put(Integer.valueOf(Item.owner.userId), Item.owner.userName);
      }
    }
    MessageWriter ClientMessage = new MessageWriter(500 + owners.size() * 24 + WallItems.size() * 70);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(owners.size()), ClientMessage);
    for (Integer id : owners.keySet())
    {
      Composer.add(id, ClientMessage);
      Composer.add(owners.get(id), ClientMessage);
    }
    Composer.add(Integer.valueOf(WallItems.size()), ClientMessage);
    for (GenericWallItem Item : WallItems) {
      SerializeWallItem.parse(ClientMessage, Item);
    }
    Composer.endPacket(ClientMessage);
    
    return ClientMessage;
  }
}


