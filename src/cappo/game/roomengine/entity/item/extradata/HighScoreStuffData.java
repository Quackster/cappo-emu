package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;

public class HighScoreStuffData
  extends ExtraDataBase
{
  public static final int TYPE_ID = 6;
  
  public int getType()
  {
    return 6;
  }
  
  public byte[] data()
  {
    return null;
  }
  
  public void serializeComposer(MessageWriter writer) {}
}


