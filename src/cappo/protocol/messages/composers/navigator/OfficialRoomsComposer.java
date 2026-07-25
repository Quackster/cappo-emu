package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.game.navigator.officialrooms.Official;
import cappo.game.navigator.officialrooms.OfficialRooms;
import cappo.protocol.messages.Composer;
import java.util.List;
import java.util.Map;

public class OfficialRoomsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
    throws Exception
  {
    MessageWriter message = new MessageWriter(1000 + OfficialRooms.SIZE * 500);
    Composer.initPacket(HEADER, message);
    Composer.add(message.setSaved(Integer.valueOf(0)), message);
    message.writeSaved(Integer.valueOf(dumpTab((List)OfficialRooms.items.get(Integer.valueOf(0)), message)));
    Composer.add(Integer.valueOf(0), message);
    Composer.add(Integer.valueOf(0), message);
    Composer.endPacket(message);
    return message;
  }
  
  private static int dumpTab(List<Official> items, MessageWriter message)
    throws Exception
  {
    int size = 0;
    if (items != null) {
      for (Official official : items)
      {
        Composer.add(Integer.valueOf(official.id), message);
        Composer.add(official.caption, message);
        Composer.add(official.desc, message);
        Composer.add(Integer.valueOf(official.showDetails ? 1 : 0), message);
        Composer.add(official.caption, message);
        Composer.add(official.image, message);
        Composer.add(Integer.valueOf(official.parentId), message);
        official.compose(message);
        if (OfficialRooms.items.containsKey(Integer.valueOf(official.id))) {
          size += dumpTab((List)OfficialRooms.items.get(Integer.valueOf(official.id)), message);
        }
        size++;
      }
    }
    return size;
  }
}


