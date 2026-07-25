package cappo.protocol.messages.composers.inventory.furni;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeItemData;
import java.util.Collection;

public class FurniListComposer
{
  public static int HEADER;
  
  public static void writeObject(FloorItem obj, MessageWriter message)
  {
    Composer.add(Integer.valueOf(obj.itemId), message);
    Composer.add(obj.baseItem.Type.toUpperCase(), message);
    Composer.add(Integer.valueOf(obj.refId), message);
    Composer.add(Integer.valueOf(obj.baseItem.SpriteId), message);
    Composer.add(Integer.valueOf(obj.baseItem.itemCategory), message);
    SerializeItemData.parse(message, obj.baseItem, obj);
    Composer.add(Boolean.valueOf(obj.baseItem.AllowRecycle), message);
    Composer.add(Boolean.valueOf(obj.baseItem.AllowTrade), message);
    Composer.add(Boolean.valueOf(obj.baseItem.AllowInventoryStack), message);
    Composer.add(Boolean.valueOf(obj.baseItem.AllowMarketplaceSell), message);
    Composer.add(Integer.valueOf(-1), message);
    Composer.add(Boolean.valueOf(false), message);
    Composer.add(Integer.valueOf(-1), message);
    Composer.add("", message);
    Composer.add(Integer.valueOf(0), message);
  }
  
  public static void writeItem(WallItem obj, MessageWriter message)
  {
    Composer.add(Integer.valueOf(obj.itemId), message);
    Composer.add(obj.baseItem.Type.toUpperCase(), message);
    Composer.add(Integer.valueOf(obj.refId), message);
    Composer.add(Integer.valueOf(obj.baseItem.SpriteId), message);
    Composer.add(Integer.valueOf(obj.baseItem.itemCategory), message);
    SerializeItemData.parse(message, obj.baseItem, obj);
    Composer.add(Boolean.valueOf(obj.baseItem.AllowRecycle), message);
    Composer.add(Boolean.valueOf(obj.baseItem.AllowTrade), message);
    Composer.add(Boolean.valueOf(obj.baseItem.AllowInventoryStack), message);
    Composer.add(Boolean.valueOf(obj.baseItem.AllowMarketplaceSell), message);
    Composer.add(Integer.valueOf(-1), message);
    Composer.add(Boolean.valueOf(false), message);
    Composer.add(Integer.valueOf(-1), message);
  }
  
  public static final MessageWriter[] compose(Collection<FloorItem> objects, Collection<WallItem> items)
  {
    int objectsSize = objects.size();
    int itemsSize = items.size();
    int ammountOfFurnis = objectsSize + itemsSize;
    
    int splitCount = 1;
    if (ammountOfFurnis > 1500)
    {
      splitCount = ammountOfFurnis / 1500;
      splitCount++;
    }
    MessageWriter[] packets = new MessageWriter[splitCount];
    
    int tmp = 0;int i = -1;
    for (FloorItem obj : objects)
    {
      if ((tmp >= 1500) || (i == -1))
      {
        int size = ammountOfFurnis > 1500 ? 1500 : ammountOfFurnis;
        ammountOfFurnis -= size;
        if (i != -1) {
          Composer.endPacket(packets[i]);
        }
        packets[(++i)] = new MessageWriter(100 + size * 300);
        tmp = 0;
        
        Composer.initPacket(HEADER, packets[i]);
        Composer.add(Integer.valueOf(splitCount), packets[i]);
        Composer.add(Integer.valueOf(i), packets[i]);
        Composer.add(Integer.valueOf(size), packets[i]);
      }
      writeObject(obj, packets[i]);
      tmp++;
    }
    for (WallItem obj : items)
    {
      if ((tmp >= 1500) || (i == -1))
      {
        int size = ammountOfFurnis > 1500 ? 1500 : ammountOfFurnis;
        ammountOfFurnis -= size;
        if (i != -1) {
          Composer.endPacket(packets[i]);
        }
        packets[(++i)] = new MessageWriter(100 + size * 300);
        tmp = 0;
        
        Composer.initPacket(HEADER, packets[i]);
        Composer.add(Integer.valueOf(splitCount), packets[i]);
        Composer.add(Integer.valueOf(i), packets[i]);
        Composer.add(Integer.valueOf(size), packets[i]);
      }
      writeItem(obj, packets[i]);
      tmp++;
    }
    if (i == -1)
    {
      i = 0;
      packets[i] = new MessageWriter(100);
      Composer.initPacket(HEADER, packets[i]);
      Composer.add(Integer.valueOf(1), packets[i]);
      Composer.add(Integer.valueOf(0), packets[i]);
      Composer.add(Integer.valueOf(0), packets[i]);
    }
    Composer.endPacket(packets[i]);
    
    return packets;
  }
}


