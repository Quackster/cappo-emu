package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class CrackableExtraData
  extends ExtraDataBase
{
  public static final int TYPE_ID = 7;
  public String value;
  public int hits;
  public int target;
  
  public int getType()
  {
    return 7;
  }
  
  public CrackableExtraData(StuffDataReader reader)
  {
    this.value = reader.readString();
    this.hits = reader.readInt16();
    this.target = reader.readInt16();
  }
  
  public byte[] data()
  {
    StuffDataWriter data = new StuffDataWriter(7);
    data.writeString(this.value);
    data.writeInt16(this.hits);
    data.writeInt16(this.target);
    return data.getData();
  }
  
  public String getWallLegacyString()
  {
    if (this.value == null) {
      return "";
    }
    return this.value;
  }
  
  public void serializeComposer(MessageWriter writer)
  {
    Composer.add(this.value, writer);
    Composer.add(Integer.valueOf(this.hits), writer);
    Composer.add(Integer.valueOf(this.target), writer);
  }
}


