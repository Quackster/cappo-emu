package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.Direction8;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.protocol.messages.Composer;

public class SerializeFloorItem
{
  public static void parse(MessageWriter ClientMessage, FloorItem Item)
  {
    Composer.add(Integer.valueOf(Item.itemId), ClientMessage);
    Composer.add(Integer.valueOf(Item.baseItem.SpriteId), ClientMessage);
    Composer.add(Integer.valueOf(Item.getX()), ClientMessage);
    Composer.add(Integer.valueOf(Item.getY()), ClientMessage);
    Composer.add(Integer.valueOf(Item.getDir().getRot()), ClientMessage);
    Composer.add(Float.toString(Item.getZ()).replace(",", "."), ClientMessage);
    Composer.add(Float.toString(Item.baseItem.Height).replace(",", "."), ClientMessage);
    Composer.add(Integer.valueOf(Item.getExtraParam()), ClientMessage);
    SerializeItemData.parse(ClientMessage, Item.baseItem, Item);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(Item.owner.userId), ClientMessage);
  }
}


