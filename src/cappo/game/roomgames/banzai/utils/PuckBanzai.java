package cappo.game.roomgames.banzai.utils;

import cappo.engine.logging.Log;
import cappo.engine.threadpools.ItemTask;
import cappo.engine.threadpools.RoomTask;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomgames.RoomGamePlayer;
import cappo.protocol.messages.composers.room.engine.ObjectUpdateComposer;
import java.util.Map;

public class PuckBanzai
  extends ItemTask
{
  private Direction8 rot;
  private int type;
  private final GenericFloorItem puckItem;
  
  public PuckBanzai(GenericFloorItem item, Avatar avatar, boolean lastSteep)
  {
    super(item);
    
    this.rot = Direction8.getRot(avatar.x, avatar.y, item.getX(), item.getY());
    this.puckItem = item;
    if (avatar.roomGamePlayer != null) {
      this.type = avatar.roomGamePlayer.team;
    }
    this.puckItem.setIntData(this.type + (lastSteep ? 50 : 0));
  }
  
  private boolean isTileBocked(int xy)
  {
    RoomTask room = this.item.getRoom();
    if (!room.validTile(xy)) {
      return true;
    }
    if (!room.squareFlag.have(xy, 4)) {
      return true;
    }
    return room.squareHasUsers(xy);
  }
  
  public void run()
  {
    try
    {
      int nextX = this.puckItem.getX() + this.rot.getDiffX();
      int nextY = this.puckItem.getY() + this.rot.getDiffY();
      
      int nextXY = nextX + nextY * this.item.getRoom().model.widthX;
      if (isTileBocked(nextXY))
      {
        this.rot = this.rot.rotateDirection180Degrees();
        nextX = this.puckItem.getX() + this.rot.getDiffX();
        nextY = this.puckItem.getY() + this.rot.getDiffY();
        
        nextXY = nextX + nextY * this.item.getRoom().model.widthX;
      }
      this.item.getRoom().generateSquare(this.puckItem.getXy(), this.puckItem, false, false);
      if ((this.type > 0) && 
        (this.item.getRoom().roomData.haveFlag(64)) && 
        (this.item.getRoom().squareFlag.eventHave(nextXY, 8)))
      {
        GenericFloorItem top = (GenericFloorItem)this.item.getRoom().topFloorItems.get(Integer.valueOf(nextXY));
        if (top.getIntData() != 3 * this.type + 2) {
          TileBanzaiWork.doWork(top, this.type, this.item.getRoom());
        }
      }
      this.puckItem.setPosition(nextX, nextY, nextXY);
      this.item.getRoom().generateSquare(this.puckItem.getXy(), this.puckItem, true, false);
      if (this.puckItem.getIntData() > 49) {
        ItemTask.addTask(this, 100, 0);
      } else if (this.puckItem.getIntData() > 39) {
        ItemTask.addTask(this, 120, 0);
      } else if (this.puckItem.getIntData() > 29) {
        ItemTask.addTask(this, 160, 0);
      } else if (this.puckItem.getIntData() > 19) {
        ItemTask.addTask(this, 250, 0);
      } else if (this.puckItem.getIntData() > 9) {
        ItemTask.addTask(this, 500, 0);
      } else {
        this.puckItem.incIntData(10);
      }
      this.item.getRoom().sendMessage(ObjectUpdateComposer.compose(this.puckItem));
      this.puckItem.decIntData(10);
    }
    catch (Exception ex)
    {
      Log.printException("", ex);
    }
  }
}


