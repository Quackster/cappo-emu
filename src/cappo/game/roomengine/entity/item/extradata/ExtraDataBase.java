package cappo.game.roomengine.entity.item.extradata;

import cappo.engine.network.MessageWriter;

public abstract class ExtraDataBase
{
  public void setExtraData(Object extraData) {}
  
  public String getWallLegacyString()
  {
    return "";
  }
  
  public abstract byte[] data();
  
  public abstract int getType();
  
  public abstract void serializeComposer(MessageWriter paramMessageWriter);
}


