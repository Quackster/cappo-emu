package cappo.protocol.messages.composers.inventory.furni;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.protocol.messages.Composer;

public class FurniListAddOrUpdateComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(FloorItem obj)
  {
    MessageWriter message = new MessageWriter();
    Composer.initPacket(HEADER, message);
    FurniListComposer.writeObject(obj, message);
    Composer.endPacket(message);
    return message;
  }
  
  public static final MessageWriter compose(WallItem item)
  {
    MessageWriter message = new MessageWriter(300);
    Composer.initPacket(HEADER, message);
    FurniListComposer.writeItem(item, message);
    Composer.endPacket(message);
    return message;
  }
}


