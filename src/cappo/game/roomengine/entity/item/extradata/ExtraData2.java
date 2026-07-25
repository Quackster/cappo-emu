package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;

public class ExtraData2
  extends ExtraDataBase
{
  public static final int TYPE_ID = 4;
  
  public int getType()
  {
    return 4;
  }
  
  public byte[] data()
  {
    return new StuffDataWriter(4).getData();
  }
  
  public void serializeComposer(MessageWriter writer) {}
}


