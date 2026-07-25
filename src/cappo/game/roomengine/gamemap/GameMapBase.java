package cappo.game.roomengine.gamemap;

import cappo.engine.logging.Log;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.Square;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GameMapBase
{
  public String modelName;
  public int doorX;
  public int doorY;
  public float doorZ;
  public Direction8 DoorOrientation;
  public boolean clubOnly;
  public int widthX;
  public int heightY;
  private Map<Short, Square> floorMap;
  
  public GameMapBase(String name, int doorx, int doory, float doorz, Direction8 doorRot, boolean club)
  {
    this.modelName = name;
    this.doorX = doorx;
    this.doorY = doory;
    this.doorZ = doorz;
    this.DoorOrientation = doorRot;
    this.clubOnly = club;
  }
  
  public void initFloorMap(int size)
  {
    this.floorMap = new ConcurrentHashMap((int)(size * 0.6D), 0.9F);
  }
  
  public void generateModel(String[] Lines)
    throws Exception
  {
    this.widthX = Lines[0].length();
    this.heightY = Lines.length;
    
    int size = this.widthX * this.heightY;
    if (size > 5000) {
      throw new Exception("TOO BIG roomModel: " + size);
    }
    initFloorMap(size);
    
    int index = 0;
    for (int y = 0; y < this.heightY; y++) {
      for (int x = 0; x < this.widthX; x++)
      {
        try
        {
          if ((this.doorX == x) && (this.doorY == y))
          {
            this.floorMap.put(Short.valueOf((short)index), new Square(x, y, index, this.doorZ));
          }
          else
          {
            char tile = Lines[y].charAt(x);
            if ((tile != 'x') && (tile != 'X')) {
              this.floorMap.put(Short.valueOf((short)index), new Square(x, y, index, Integer.parseInt(Character.toString(tile), 36)));
            }
          }
        }
        catch (Exception ex)
        {
          Log.printException("", ex);
        }
        index++;
      }
    }
    buildSquares();
  }
  
  public void buildSquares()
  {
    int index = 0;
    for (int y = 0; y < this.heightY; y++) {
      for (int x = 0; x < this.widthX; x++)
      {
        try
        {
          buildAdjacencies(index);
        }
        catch (Exception ex)
        {
          Log.printException("", ex);
        }
        index++;
      }
    }
  }
  
  private void buildAdjacencies(int xy)
  {
    Square element = (Square)this.floorMap.get(Short.valueOf((short)xy));
    if (element == null) {
      return;
    }
    int Y = xy / this.widthX;
    int X = xy - Y * this.widthX;
    int maxX = this.widthX - 1;
    int maxY = this.heightY - 1;
    
    float minAdjacentHeight = element.height - 5.0F;
    float maxAdjacentHeight = element.height + 5.0F;
    if (X < maxX)
    {
      addAdjacent(element, minAdjacentHeight, maxAdjacentHeight, xy + 1, false);
      if (X > 0) {
        addAdjacent(element, minAdjacentHeight, maxAdjacentHeight, xy + this.widthX - 1, true);
      }
      if (Y < maxY) {
        addAdjacent(element, minAdjacentHeight, maxAdjacentHeight, xy + this.widthX + 1, true);
      }
    }
    if (Y < maxY) {
      addAdjacent(element, minAdjacentHeight, maxAdjacentHeight, xy + this.widthX, false);
    }
  }
  
  private void addAdjacent(Square element, float minAdjacentHeight, float maxAdjacentHeight, int xy, boolean diagonal)
  {
    Square sq = (Square)this.floorMap.get(Short.valueOf((short)xy));
    if (sq == null) {
      return;
    }
    if (sq.height > maxAdjacentHeight) {
      return;
    }
    if (sq.height < minAdjacentHeight) {
      return;
    }
    sq.adjacencies.add(element);
    element.adjacencies.add(sq);
    if (!diagonal)
    {
      sq.adjacenciesNoDiagonal.add(element);
      element.adjacenciesNoDiagonal.add(sq);
    }
  }
  
  public Square getSquare(int xy)
  {
    return (Square)this.floorMap.get(Short.valueOf((short)xy));
  }
  
  public Square setSquare(int xy, Square sq)
  {
    return (Square)this.floorMap.put(Short.valueOf((short)xy), sq);
  }
}


