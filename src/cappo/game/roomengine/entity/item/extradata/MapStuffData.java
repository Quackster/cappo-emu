package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapStuffData
  extends ExtraDataBase
{
  public static final int TYPE_ID = 1;
  public Map<String, String> extraData;
  public static final String STATE = "state";
  public static final String RARITY = "rarity";
  
  public int getType()
  {
    return 1;
  }
  
  public MapStuffData(StuffDataReader data)
  {
    int len = data.readInt8();
    
    this.extraData = new ConcurrentHashMap(1 + (int)(len * 1.2D));
    for (int i = 0; i < len; i++) {
      this.extraData.put(data.readString(), data.readString());
    }
  }
  
  public MapStuffData(String data)
  {
    this.extraData = new ConcurrentHashMap();
    setExtraData(data);
  }
  
  public byte[] data()
  {
    StuffDataWriter data = new StuffDataWriter(1);
    data.writeInt8(this.extraData.size());
    for (String key : this.extraData.keySet())
    {
      data.writeString(key);
      data.writeString((String)this.extraData.get(key));
    }
    return data.getData();
  }
  
  public void serializeComposer(MessageWriter writer)
  {
    Composer.add(Integer.valueOf(this.extraData.size()), writer);
    for (String key : this.extraData.keySet())
    {
      Composer.add(key, writer);
      Composer.add(this.extraData.get(key), writer);
    }
  }
  
  public void setExtraData(Object data)
  {
    String sData = (String)data;
    
    String[] values = sData.split("\t");
    for (String part : values) {
      if ((!part.isEmpty()) && (!part.equals("=")))
      {
        String[] a = part.split("=");
        if (a.length == 2) {
          this.extraData.put(a[0], a[1]);
        }
      }
    }
  }
  
  public String getWallLegacyString()
  {
    if (this.extraData == null) {
      return "";
    }
    String data = (String)this.extraData.get("state");
    if (data == null) {
      return "";
    }
    return data;
  }
}


