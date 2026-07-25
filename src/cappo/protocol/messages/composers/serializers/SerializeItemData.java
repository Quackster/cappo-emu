package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.Item;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.protocol.messages.Composer;

public class SerializeItemData
{
  public static void parse(MessageWriter writer, BaseItem baseItem, Item item)
  {
    Composer.add(Integer.valueOf(item.extraData.getType()), writer);
    item.extraData.serializeComposer(writer);
  }
}


