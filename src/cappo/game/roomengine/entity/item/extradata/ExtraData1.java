package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ExtraData1
  extends ExtraDataBase
{
  public static final int TYPE_ID = 3;
  public String value;
  public int result;
  
  public int getType()
  {
    return 3;
  }
  
  public ExtraData1(StuffDataReader reader)
  {
    this.value = reader.readString();
    this.result = reader.readInt16();
  }
  
  public byte[] data()
  {
    StuffDataWriter data = new StuffDataWriter(3);
    data.writeString(this.value);
    data.writeInt16(this.result);
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
    Composer.add(Integer.valueOf(this.result), writer);
  }
}


