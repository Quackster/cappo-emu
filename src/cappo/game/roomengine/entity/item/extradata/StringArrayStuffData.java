package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.ArrayList;
import java.util.List;

public class StringArrayStuffData
  extends ExtraDataBase
{
  public static final int TYPE_ID = 2;
  public List<String> extraData;
  
  public int getType()
  {
    return 2;
  }
  
  public StringArrayStuffData(StuffDataReader data)
  {
    int len = data.readInt8();
    
    this.extraData = new ArrayList(1 + (int)(len * 1.2D));
    for (int i = 0; i < len; i++) {
      this.extraData.add(data.readString());
    }
  }
  
  public byte[] data()
  {
    StuffDataWriter data = new StuffDataWriter(2);
    data.writeInt8(this.extraData.size());
    for (String value : this.extraData) {
      data.writeString(value);
    }
    return data.getData();
  }
  
  public String getWallLegacyString()
  {
    if ((this.extraData == null) || (this.extraData.isEmpty())) {
      return "";
    }
    return (String)this.extraData.get(0);
  }
  
  public void serializeComposer(MessageWriter writer)
  {
    Composer.add(Integer.valueOf(this.extraData.size()), writer);
    for (String value : this.extraData) {
      Composer.add(value, writer);
    }
  }
}


