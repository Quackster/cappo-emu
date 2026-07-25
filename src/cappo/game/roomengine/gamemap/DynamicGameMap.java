package cappo.game.roomengine.gamemap;

import cappo.engine.logging.Log;
import cappo.game.roomengine.Square;

public class DynamicGameMap
  extends GameMapBase
{
  public DynamicGameMap(GameMapBase base, int sizeX, int sizeY)
    throws Exception
  {
    super(base.modelName, base.doorX, base.doorY, base.doorZ, base.DoorOrientation, base.clubOnly);
    
    this.widthX = sizeX;
    this.heightY = sizeY;
    
    initFloorMap(this.widthX * this.heightY);
    
    int index = 0;
    for (int y = 0; y < base.heightY; y++) {
      for (int x = 0; x < base.widthX; x++)
      {
        try
        {
          int newXY = x + y * this.widthX;
          if ((this.doorX == x) && (this.doorY == y))
          {
            setSquare(newXY, new Square(x, y, newXY, this.doorZ));
          }
          else
          {
            Square sq = base.getSquare(index);
            if (sq != null) {
              setSquare(newXY, sq);
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
  }
}


