package cappo.game.roomgames.football.utils;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.logging.Log;
import cappo.engine.threadpools.ItemTask;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.composers.room.action.AvatarExpressionComposer;
import cappo.protocol.messages.composers.room.engine.ObjectUpdateComposer;

public class BallonFootBall
  extends ItemTask
{
  private Direction8 rot;
  private final Avatar avatar;
  private final GenericFloorItem ballItem;
  
  public BallonFootBall(GenericFloorItem item, Avatar avt, boolean lastSteep)
  {
    super(item);
    
    this.rot = Direction8.getRot(avt.x, avt.y, item.getX(), item.getY());
    this.ballItem = item;
    this.avatar = avt;
    this.ballItem.setIntData(lastSteep ? 55 : 0);
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
      boolean goal = false;
      
      RoomTask room = this.item.getRoom();
      
      int nextX = this.ballItem.getX() + this.rot.getDiffX();
      int nextY = this.ballItem.getY() + this.rot.getDiffY();
      
      int nextXY = nextX + nextY * room.model.widthX;
      if (isTileBocked(nextXY))
      {
        this.rot = this.rot.rotateDirection180Degrees();
        nextX = this.ballItem.getX() + this.rot.getDiffX();
        nextY = this.ballItem.getY() + this.rot.getDiffY();
        
        nextXY = nextX + nextY * room.model.widthX;
      }
      room.generateSquare(this.ballItem.getXy(), this.ballItem, false, false);
      if (room.squareFlag.eventHave(nextXY, 64))
      {
        goal = true;
        if (this.item.baseItem.interactorType == Interactor.InteractorType.banzaiscoreblue)
        {
          room.ScorePoints_B += 1;
          for (GenericFloorItem item : room.roomGamesScorersBLUE)
          {
            item.setIntData(room.ScorePoints_B);
            room.floorItemUpdateNeeded(item);
          }
        }
        else if (this.item.baseItem.interactorType == Interactor.InteractorType.banzaiscoregreen)
        {
          room.ScorePoints_G += 1;
          for (GenericFloorItem item : room.roomGamesScorersGREEN)
          {
            item.setIntData(room.ScorePoints_G);
            room.floorItemUpdateNeeded(item);
          }
        }
        else if (this.item.baseItem.interactorType == Interactor.InteractorType.banzaiscorered)
        {
          room.ScorePoints_R += 1;
          for (GenericFloorItem item : room.roomGamesScorersRED)
          {
            item.setIntData(room.ScorePoints_R);
            room.floorItemUpdateNeeded(item);
          }
        }
        else if (this.item.baseItem.interactorType == Interactor.InteractorType.banzaiscoreyellow)
        {
          room.ScorePoints_Y += 1;
          for (GenericFloorItem item : room.roomGamesScorersYELLOW)
          {
            item.setIntData(room.ScorePoints_Y);
            room.floorItemUpdateNeeded(item);
          }
        }
        room.sendMessage(AvatarExpressionComposer.compose(this.avatar.virtualId, 7));
      }
      this.ballItem.setPosition(nextX + nextY * room.model.widthX);
      room.generateSquare(this.ballItem.getXy(), this.ballItem, true, false);
      if (goal)
      {
        this.ballItem.setIntData(11);
        room.sendMessage(ObjectUpdateComposer.compose(this.ballItem));
        return;
      }
      if (this.ballItem.getIntData() == 55) {
        ItemTask.addTask(this, 100, 0);
      } else if (this.ballItem.getIntData() == 44) {
        ItemTask.addTask(this, 100, 0);
      } else if (this.ballItem.getIntData() == 33) {
        ItemTask.addTask(this, 200, 0);
      } else if (this.ballItem.getIntData() == 22) {
        ItemTask.addTask(this, 250, 0);
      } else if (this.ballItem.getIntData() == 11) {
        ItemTask.addTask(this, 500, 0);
      } else {
        this.ballItem.setIntData(11);
      }
      room.sendMessage(ObjectUpdateComposer.compose(this.ballItem));
      

      this.ballItem.decIntData(11);
    }
    catch (Exception ex)
    {
      Log.printException("", ex);
    }
  }
}
