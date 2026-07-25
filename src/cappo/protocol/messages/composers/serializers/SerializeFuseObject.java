package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.GamefuseObject;
import cappo.game.games.snowwar.Tile;
import cappo.protocol.messages.Composer;

public class SerializeFuseObject
{
  public static void parse(MessageWriter ClientMessage, GamefuseObject fuseItem)
  {
    Composer.add(fuseItem.baseItem.Name, ClientMessage);
    Composer.add(Integer.valueOf(fuseItem.itemId), ClientMessage);
    Composer.add(Integer.valueOf(fuseItem.X), ClientMessage);
    Composer.add(Integer.valueOf(fuseItem.Y), ClientMessage);
    Composer.add(Integer.valueOf(fuseItem.baseItem.xDim), ClientMessage);
    Composer.add(Integer.valueOf(fuseItem.baseItem.yDim), ClientMessage);
    Composer.add(Integer.valueOf((int)(fuseItem.baseItem.Height * Tile.TILE_SIZE)), ClientMessage);
    Composer.add(Integer.valueOf(fuseItem.Rot), ClientMessage);
    Composer.add(Integer.valueOf(fuseItem.Z), ClientMessage);
    Composer.add(Boolean.valueOf(fuseItem.baseItem.allowWalk), ClientMessage);
    SerializeItemData.parse(ClientMessage, fuseItem.baseItem, fuseItem);
  }
}


