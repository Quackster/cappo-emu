package cappo.game.games.snowwar;

import cappo.game.games.snowwar.gameobjects.GameItemObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SynchronizedGameStage
{
  public Map<Integer, GameItemObject> gameObjects;
  private final List<GameItemObject> _2xj;
  public int objectIdCounter;
  
  public SynchronizedGameStage()
  {
    this.gameObjects = new LinkedHashMap();
    this._2xj = new ArrayList();
  }
  
  public void addGameObject(GameItemObject obj)
  {
    if (obj.objectId == 0) {
      obj.objectId = (this.objectIdCounter++);
    }
    this.gameObjects.put(Integer.valueOf(obj.objectId), obj);
    obj._active = true;
  }
  
  public void removeGameObject(int _arg1)
  {
    GameItemObject local1 = (GameItemObject)this.gameObjects.remove(Integer.valueOf(_arg1));
    if (local1 != null) {
      local1.onRemove();
    }
  }
  
  public void queueDeleteObject(GameItemObject _arg1)
  {
    if (_arg1 == null) {
      return;
    }
    this._2xj.add(_arg1);
    _arg1._active = false;
    _arg1.GenerateCHECKSUM((SnowWarRoom)this, -1);
  }
  
  public GameItemObject _3Pl(int _arg1)
  {
    return (GameItemObject)this.gameObjects.get(Integer.valueOf(_arg1));
  }
  
  public void subturn()
  {
    for (GameItemObject local0 : this.gameObjects.values()) {
      local0.subturn(this);
    }
    if (!this._2xj.isEmpty())
    {
      for (GameItemObject local1 : this._2xj) {
        removeGameObject(local1.objectId);
      }
      this._2xj.clear();
    }
  }
}


