package cappo.game.roomengine.entity.item.wall;

import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;

public class GenericWallItem
  extends WallItem
{
  private int intData;
  
  public int getIntData()
  {
    return this.intData;
  }
  
  public void setIntData(int data)
  {
    this.intData = data;
    this.extraData.setExtraData(Integer.valueOf(this.intData));
  }
  
  public int incIntData(int ammount)
  {
    this.intData += ammount;
    this.extraData.setExtraData(Integer.valueOf(this.intData));
    return this.intData;
  }
  
  public int decIntData(int ammount)
  {
    this.intData -= ammount;
    this.extraData.setExtraData(Integer.valueOf(this.intData));
    return this.intData;
  }
}


