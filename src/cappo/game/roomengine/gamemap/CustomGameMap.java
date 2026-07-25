package cappo.game.roomengine.gamemap;

import cappo.engine.logging.Log;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.Square;

public class CustomGameMap
  extends GameMapBase
{
  public String baseName;
  public int mysqlAction;
  
  public CustomGameMap(String name, int doorx, int doory, float doorz, Direction8 doorRot, boolean club)
  {
    super(name, doorx, doory, doorz, doorRot, club);
  }
  
  public String buildHeightMap()
  {
    char[] map = new char[this.widthX * this.heightY + this.heightY];
    
    int pos = 0;
    int xy = 0;
    for (int y = 0; y < this.heightY; y++)
    {
      for (int x = 0; x < this.widthX; x++)
      {
        try
        {
          Square sq = getSquare(xy);
          if (sq == null) {
            map[pos] = 'x';
          } else {
            map[pos] = Integer.toString((int)sq.height, 36).charAt(0);
          }
        }
        catch (Exception ex)
        {
          map[pos] = 'x';
          Log.printException("", ex);
        }
        xy++;
        pos++;
      }
      map[pos] = '\n';
      pos++;
    }
    return new String(map);
  }
}


