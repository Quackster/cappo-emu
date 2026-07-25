package cappo.game.roomengine.entity.item.floor;

import cappo.engine.database.Database;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BflyData;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.entity.item.RoomItemData;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.itemInteractor.Interactor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomFloorItemData
  extends RoomItemData
{
  private final FloorItem floorItem;
  private int x;
  private int y;
  private int xy;
  private Direction8 dir = Direction8.N;
  private float z;
  
  public RoomFloorItemData(RoomTask room, FloorItem item)
  {
    super(item.baseItem, room);
    this.floorItem = item;
  }
  
  public void setPosition()
  {
    setXy(this.x + this.y * this.currentRoom.model.widthX);
  }
  
  public void setPosition(int argX, int argY, int argXY)
  {
    setX(argX);
    setY(argY);
    setXy(argXY);
  }
  
  public void setPosition(int x, int y)
  {
    setXy(x + y * this.currentRoom.model.widthX);
    

    setX(x);
    setY(y);
  }
  
  public void setPosition(int xy)
  {
    setXy(xy);
    

    setY(xy / this.currentRoom.model.widthX);
    setX(xy - this.y * this.currentRoom.model.widthX);
  }
  
  public void setPosition(float val)
  {
    setZ(val);
  }
  
  public List<AffectedTile> getOutSideTiles()
  {
    int mapSizeX = this.currentRoom.model.widthX;
    int mapSizeY = this.currentRoom.model.heightY;
    
    boolean swap = Direction8.haveDirection(this.dir, new Direction8[] { Direction8.N, Direction8.S });
    int xDim = swap ? this.baseItem.xDim : this.baseItem.yDim;
    int yDim = swap ? this.baseItem.yDim : this.baseItem.xDim;
    
    List<AffectedTile> PointList = new ArrayList();
    if ((this.x > 0) && (this.y > 0))
    {
      int newXY2 = this.xy - 1 - mapSizeX;
      if (this.currentRoom.validTile(newXY2)) {
        PointList.add(new AffectedTile(newXY2, mapSizeX));
      }
    }
    boolean yFree = getY() + yDim < mapSizeY;
    for (int i = 0; i <= xDim; i++)
    {
      if (yFree)
      {
        int newXY2 = this.xy + i + yDim * mapSizeX;
        if (this.currentRoom.validTile(newXY2)) {
          PointList.add(new AffectedTile(newXY2, mapSizeX));
        }
      }
      if (getY() > 0)
      {
        int newXY2 = this.xy + i - mapSizeX;
        if (this.currentRoom.validTile(newXY2)) {
          PointList.add(new AffectedTile(newXY2, mapSizeX));
        }
      }
    }
    boolean xFree = getX() + xDim < mapSizeX;
    for (int i = 0; i <= yDim; i++)
    {
      if (xFree)
      {
        int newXY2 = this.xy + xDim + i * mapSizeX;
        if (this.currentRoom.validTile(newXY2)) {
          PointList.add(new AffectedTile(newXY2, mapSizeX));
        }
      }
      if (getX() > 0)
      {
        int newXY2 = this.xy - 1 + i * mapSizeX;
        if (this.currentRoom.validTile(newXY2)) {
          PointList.add(new AffectedTile(newXY2, mapSizeX));
        }
      }
    }
    return PointList;
  }
  
  public boolean itemsOnTop()
  {
    List<AffectedTile> PointList = getAffectedTiles();
    for (AffectedTile Tile : PointList)
    {
      float tmp = getZ() + this.baseItem.Height;
      Float f = (Float)this.currentRoom.squareAbsoluteHeight.get(Integer.valueOf(Tile.xy));
      if ((f != null) && (tmp < f.floatValue())) {
        return true;
      }
    }
    return false;
  }
  
  public List<AffectedTile> getAffectedTiles()
  {
    return getAffectedTiles(this.xy, this.dir);
  }
  
  public List<AffectedTile> getAffectedTiles(boolean asd)
  {
    return getAffectedTiles(this.x, this.y, this.dir);
  }
  
  public List<AffectedTile> getAffectedTiles(int xy, Direction8 dir)
  {
    int mapSizeX = this.currentRoom.model.widthX;
    
    boolean swap = Direction8.haveDirection(dir, new Direction8[] { Direction8.N, Direction8.S });
    int xDim = swap ? this.baseItem.xDim : this.baseItem.yDim;
    int yDim = swap ? this.baseItem.yDim : this.baseItem.xDim;
    
    List<AffectedTile> PointList = new ArrayList();
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++)
      {
        int newXY2 = xy + i + j * mapSizeX;
        PointList.add(new AffectedTile(newXY2, mapSizeX));
      }
    }
    return PointList;
  }
  
  public List<AffectedTile> getAffectedTiles(int x, int y, Direction8 dir)
  {
    boolean swap = Direction8.haveDirection(dir, new Direction8[] { Direction8.N, Direction8.S });
    int xDim = swap ? this.baseItem.xDim : this.baseItem.yDim;
    int yDim = swap ? this.baseItem.yDim : this.baseItem.xDim;
    
    List<AffectedTile> PointList = new ArrayList();
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++)
      {
        int vX = x + i;
        int vY = y + j;
        PointList.add(new AffectedTile(vX, vY, true));
      }
    }
    return PointList;
  }
  
  public byte[] SquareInFront()
  {
    return new byte[] { (byte)(this.x + this.dir.getDiffX()), (byte)(this.y + this.dir.getDiffY()) };
  }
  
  public byte[] SquareBehind()
  {
    Direction8 tmp = this.dir.rotateDirection180Degrees();
    return new byte[] { (byte)(this.x + tmp.getDiffX()), (byte)(this.y + tmp.getDiffY()) };
  }
  
  public void finishPlace(List<AffectedTile> Points)
  {
    for (AffectedTile Tile : Points)
    {
      int xy = Tile.x + Tile.y * this.currentRoom.model.widthX;
      this.currentRoom.generateSquare(xy, this.floorItem, true, true);
    }
    if ((this.floorItem instanceof GenericFloorItem)) {
      this.baseItem.interactor.OnPlace(this.currentRoom, null, (GenericFloorItem)this.floorItem);
    }
    this.currentRoom.FloorItems.put(Integer.valueOf(this.floorItem.itemId), this.floorItem);
  }
  
  public void finishPlace(Connection user, List<AffectedTile> Points, boolean add)
  {
    for (AffectedTile Tile : Points) {
      this.currentRoom.generateSquare(Tile.xy, this.floorItem, true, false);
    }
    if (add)
    {
      if ((this.floorItem instanceof GenericFloorItem)) {
        this.baseItem.interactor.OnPlace(this.currentRoom, user, (GenericFloorItem)this.floorItem);
      }
      this.currentRoom.FloorItems.put(Integer.valueOf(this.floorItem.itemId), this.floorItem);
    }
  }
  
  public static class AffectedTile
  {
    public int x;
    public int y;
    public int xy;
    
    public AffectedTile(int XY, int mapSizeX)
    {
      this.y = (XY / mapSizeX);
      this.x = (XY - this.y * mapSizeX);
      this.xy = XY;
    }
    
    public AffectedTile(int X, int Y, boolean n)
    {
      this.x = X;
      this.y = Y;
    }
  }
  
  public void save()
    throws Exception
  {
    Database.exec("UPDATE furnis_roomdata SET a='" + BflyData.Combine(this.x, this.y) + "',b='" + this.z + "',r=" + this.dir.getRot() + " WHERE id=" + this.floorItem.itemId + ";", new Object[0]);
  }
  
  public int getX()
  {
    return this.x;
  }
  
  public void setX(int x)
  {
    this.x = x;
  }
  
  public int getY()
  {
    return this.y;
  }
  
  public void setY(int y)
  {
    this.y = y;
  }
  
  public int getXy()
  {
    return this.xy;
  }
  
  public void setXy(int xy)
  {
    this.xy = xy;
  }
  
  public Direction8 getDir()
  {
    return this.dir;
  }
  
  public void setDir(Direction8 dir)
  {
    this.dir = dir;
  }
  
  public float getZ()
  {
    return this.z;
  }
  
  public void setZ(float z)
  {
    this.z = z;
  }
}


