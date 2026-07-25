package cappo.game.games.snowwar;

import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.gameobjects.GameItemObject;
import java.util.List;

public class SnowWarGameStage
{
  public static int _43q = 25;
  public static int _3hO = 25;
  private static int _0Fs = 100000;
  private Tile[][] tileMap;
  
  public void initialize(SnowWarArenaBase arena)
  {
    buildMap(arena);
    addObjects(arena.fuseObjects);
  }
  
  public static Direction8 _4Ce(Tile _arg1)
  {
    return Direction360.direction360ValueToDirection8(Direction360.getRot(_43q - _arg1._4gH[0], _3hO - _arg1._4gH[1]));
  }
  
  public void _2Av(GameItemObject _arg1)
  {
    PlayerTile local1 = _arg1.location3D();
    Tile local2 = getTile(Tile._4mC(local1.x()), Tile._3FS(local1.y()));
    if (local2 != null) {
      local2._1tH(_arg1);
    }
  }
  
  public boolean _18P(int _arg1, int _arg2)
  {
    int local1 = Tile._4mC(_arg1);
    int local2 = Tile._3FS(_arg2);
    Tile local3 = getTile(local1, local2);
    if (local3 != null) {
      return local3.isOpen(null);
    }
    return false;
  }
  
  public boolean checkFloorCollision(GameItemObject _arg1)
  {
    if (_arg1.location3D().z() < 1) {
      return true;
    }
    int local1 = Tile._4mC(_arg1.location3D().x());
    int local2 = Tile._3FS(_arg1.location3D().y());
    Tile local3 = getTile(local1, local2);
    if (local3 != null) {
      return _arg1.location3D().z() < local3.height();
    }
    return false;
  }
  
  public Tile getTile(int _arg1, int _arg2)
  {
    if ((_arg1 < 0) || (_arg1 >= this.tileMap[0].length) || 
      (_arg2 < 0) || (_arg2 >= this.tileMap.length)) {
      return null;
    }
    return this.tileMap[_arg2][_arg1];
  }
  
  private void addObjects(List<GamefuseObject> objects)
  {
    for (GamefuseObject object : objects)
    {
      Tile _arg3 = getTile(object.X, object.Y);
      if (_arg3 != null)
      {
        _arg3._lR(object);
        setupTile(object);
      }
    }
  }
  
  private void setupTile(GamefuseObject object)
  {
    int local2 = object.Rot;
    int local3 = object.baseItem.xDim;
    int local4 = object.baseItem.yDim;
    if ((local2 == Direction8.E.getRot()) || (local2 == Direction8.W.getRot()))
    {
      int local6 = local3;
      local3 = local4;
      local4 = local6;
    }
    int local5 = 1;
    while (local5 < local3)
    {
      Tile local1 = getTile(object.X + local5, object.Y);
      if (local1 != null)
      {
        local1._4AO((int)(object.baseItem.Height * Tile.TILE_SIZE));
        if (!object.baseItem.allowWalk) {
          local1.setBlocked(true);
        }
      }
      local5++;
    }
    local5 = 1;
    while (local5 < local4)
    {
      Tile local1 = getTile(object.X, object.Y + local5);
      if (local1 != null)
      {
        local1._4AO((int)(object.baseItem.Height * Tile.TILE_SIZE));
        if (!object.baseItem.allowWalk) {
          local1.setBlocked(true);
        }
      }
      local5++;
    }
  }
  
  private void buildMap(SnowWarArenaBase _arg1)
  {
    int[][] local1 = parseHeightMap(_arg1.HeightMap, _arg1.ArenaWidth, _arg1.ArenaHeight);
    int local2 = _arg1.ArenaHeight;
    int local3 = _arg1.ArenaWidth;
    this.tileMap = new Tile[_arg1.ArenaHeight][];
    int local5 = 0;
    while (local5 < local2)
    {
      this.tileMap[local5] = new Tile[_arg1.ArenaWidth];
      int local6 = 0;
      while (local6 < local3)
      {
        this.tileMap[local5][local6] = null;
        if (local1[local5][local6] != _0Fs)
        {
          Tile local4 = new Tile(local6, local5);
          this.tileMap[local5][local6] = local4;
          Tile local7 = getTile(local6 + 1, local5 - 1);
          if (local7 != null) {
            local4._3iT(local7, Direction8.NE);
          }
          Tile local8 = getTile(local6, local5 - 1);
          if (local8 != null) {
            local4._3iT(local8, Direction8.N);
          }
          Tile local9 = getTile(local6 - 1, local5 - 1);
          if (local9 != null) {
            local4._3iT(local9, Direction8.NW);
          }
          Tile local10 = getTile(local6 - 1, local5);
          if (local10 != null) {
            local4._3iT(local10, Direction8.W);
          }
        }
        local6++;
      }
      local5++;
    }
  }
  
  private int[][] parseHeightMap(String _arg1, int _arg2, int _arg3)
  {
    int local3 = 0;
    String[] local4 = _arg1.split("\r");
    int[][] local5 = new int[local4.length][];
    int local6 = 0;
    while (local6 < local4.length)
    {
      String local7 = local4[local6];
      local5[local6] = new int[local7.length()];
      int local8 = local7.length() - 1;
      while (local8 >= 0)
      {
        String local9 = local7.substring(local8, local8 + 1);
        if (local9.equals("x")) {
          local5[local6][local8] = _0Fs;
        } else {
          try
          {
            local5[local6][local8] = Integer.parseInt(local9);
          }
          catch (Exception ex)
          {
            local5[local6][local8] = (10 + (local9.charAt(0) - 'a'));
          }
        }
        if ((local5[local6][local8] > local3) && (local5[local6][local8] != _0Fs)) {
          local3 = local5[local6][local8];
        }
        local8--;
      }
      local6++;
    }
    return local5;
  }
}


