package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.protocol.messages.Composer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HeightMapUpdateComposer
{
  public static int HEADER;
  private static final int MIN_CHAR = 32;
  private static final int MAX_CHAR = 127;
  private static final int MAX_NUM = 95;
  
  public static final MessageWriter compose(RoomTask room, List<FloorItem> items)
  {
    Map<Integer, FloorItem> points = new HashMap();
    for (FloorItem item : items) {
      points.put(Integer.valueOf(item.getXy()), item);
    }
    int currentPos = 0;
    
    String data = "";
    List<Integer> orderedItems = new ArrayList(points.keySet());
    Collections.sort(orderedItems);
    for (Iterator localIterator2 = orderedItems.iterator(); localIterator2.hasNext();)
    {
      int key = ((Integer)localIterator2.next()).intValue();
      FloorItem item = (FloorItem)points.get(Integer.valueOf(key));
      
      key -= currentPos;
      currentPos += key;
      do
      {
        if (key > 95)
        {
          data = data + "!";
          key -= 95;
        }
        else
        {
          data = data + "!" + (char)(key + 32);
        }
      } while (
      






        key > 0);
      float floorHeight = ((Float)room.squareAbsoluteHeight.get(Integer.valueOf(item.getXy()))).floatValue();
      if (!room.squareFlag.have(item.getXy(), 4)) {
        floorHeight += 10.0F;
      }
      data = data + (char)(int)floorHeight;
    }
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(data, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


