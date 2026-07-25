package cappo.game.roomengine.entity.item.wall;

import cappo.engine.database.Database;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BflyData;
import cappo.game.roomengine.entity.item.RoomItemData;

public class RoomWallItemData
  extends RoomItemData
{
  public WallItem wallItem;
  public int widthX;
  public int widthY;
  public int lengthX;
  public int lengthY;
  public char side;
  
  public RoomWallItemData(RoomTask room, WallItem item, char side, int widthX, int widthY, int lengthX, int lengthY)
  {
    super(item.baseItem, room);
    
    this.wallItem = item;
    
    this.side = side;
    this.widthX = widthX;
    this.widthY = widthY;
    this.lengthX = lengthX;
    this.lengthY = lengthY;
  }
  
  public RoomWallItemData(RoomTask room, BaseItem base, float x, float y, int n)
  {
    super(base, room);
    
    this.widthY = BflyData.getB(x);
    this.widthX = BflyData.getA(x, this.widthY);
    
    this.lengthY = BflyData.getB(y);
    this.lengthX = BflyData.getA(y, this.lengthY);
    if (n == 7) {
      this.side = 'r';
    } else {
      this.side = 'l';
    }
  }
  
  public String toString()
  {
    return ":w=" + this.widthX + "," + this.widthY + " " + "l=" + this.lengthX + "," + this.lengthY + " " + this.side;
  }
  
  public float GetXValue()
  {
    return BflyData.Combine(this.widthX, this.widthY);
  }
  
  public float GetYValue()
  {
    return BflyData.Combine(this.lengthX, this.lengthY);
  }
  
  public int n()
  {
    if (this.side == 'l') {
      return 8;
    }
    return 7;
  }
  
  public void save()
    throws Exception
  {
    Database.exec("UPDATE furnis_roomdata SET a='" + GetXValue() + "',b='" + GetYValue() + "',r=" + n() + " WHERE id=" + this.wallItem.itemId + ";", new Object[0]);
  }
}


