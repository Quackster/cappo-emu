package cappo.game.games.snowwar.gameobjects;

import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.CollisionDetection;
import cappo.game.games.snowwar.Direction360;
import cappo.game.games.snowwar.PlayerTile;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.SynchronizedGameStage;

public abstract class GameItemObject
{
  public static final int SNOWBALL = 1;
  public static final int TREE = 2;
  public static final int PILE = 3;
  public static final int MACHINE = 4;
  public static final int HUMAN = 5;
  public BaseItem BaseItem;
  public Object Data;
  public int objectId;
  public boolean _active = false;
  public final int variablesCount;
  
  public abstract PlayerTile location3D();
  
  public abstract Direction360 direction360();
  
  public abstract int getVariable(int paramInt);
  
  public abstract int[] boundingData();
  
  public void onRemove() {}
  
  public void subturn(SynchronizedGameStage _arg1) {}
  
  public void onSnowBallHit(SnowBallGameObject _arg2) {}
  
  protected GameItemObject(int varCount)
  {
    this.variablesCount = varCount;
  }
  
  public void GenerateCHECKSUM(SnowWarRoom arena, int multipler)
  {
    for (int i = 0; i < this.variablesCount;) {
      arena.checksum += getVariable(i) * ++i * multipler;
    }
  }
  
  public int _3au()
  {
    return -(this.objectId + 1);
  }
  
  public int collisionHeight()
  {
    return boundingData()[0];
  }
  
  public boolean testSnowBallCollision(SnowBallGameObject _arg1)
  {
    return (_arg1.location3D().z() < collisionHeight()) && (CollisionDetection._pU(this, _arg1));
  }
}


