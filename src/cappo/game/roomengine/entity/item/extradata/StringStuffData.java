package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class StringStuffData
  extends ExtraDataBase
{
  public static final int TYPE_ID = 0;
  public String extraData;
  
  public int getType()
  {
    return 0;
  }
  
  public StringStuffData(StuffDataReader data)
  {
    if (data == null) {
      this.extraData = "";
    } else {
      this.extraData = data.readString();
    }
  }
  
  public byte[] data()
  {
    if (this.extraData.isEmpty()) {
      return null;
    }
    StuffDataWriter data = new StuffDataWriter(0);
    data.writeString(this.extraData);
    return data.getData();
  }
  
  public void setExtraData(Object data)
  {
    if ((data instanceof Integer)) {
      this.extraData = Integer.toString(((Integer)data).intValue());
    } else {
      this.extraData = ((String)data);
    }
  }
  
  public String getWallLegacyString()
  {
    return this.extraData;
  }
  
  public void serializeComposer(MessageWriter writer)
  {
    Composer.add(this.extraData, writer);
  }
}


