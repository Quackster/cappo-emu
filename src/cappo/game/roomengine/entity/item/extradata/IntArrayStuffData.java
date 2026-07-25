package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntArrayStuffData
  extends ExtraDataBase
{
  public static final int TYPE_ID = 5;
  public List<Integer> extraData;
  
  public int getType()
  {
    return 5;
  }
  
  public IntArrayStuffData(StuffDataReader data)
  {
    int len = data.readInt8();
    
    this.extraData = new ArrayList(1 + (int)(len * 1.2D));
    for (int i = 0; i < len; i++) {
      this.extraData.add(Integer.valueOf(data.readInt32()));
    }
  }
  
  public byte[] data()
  {
    StuffDataWriter data = new StuffDataWriter(5);
    data.writeInt8(this.extraData.size());
    for (Iterator localIterator = this.extraData.iterator(); localIterator.hasNext();)
    {
      int value = ((Integer)localIterator.next()).intValue();
      data.writeInt32(value);
    }
    return data.getData();
  }
  
  public String getWallLegacyString()
  {
    if ((this.extraData == null) || (this.extraData.isEmpty())) {
      return "";
    }
    return Integer.toString(((Integer)this.extraData.get(0)).intValue());
  }
  
  public void serializeComposer(MessageWriter writer)
  {
    Composer.add(Integer.valueOf(this.extraData.size()), writer);
    for (Iterator localIterator = this.extraData.iterator(); localIterator.hasNext();)
    {
      int value = ((Integer)localIterator.next()).intValue();
      Composer.add(Integer.valueOf(value), writer);
    }
  }
}


