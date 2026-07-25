package cappo.game.roomengine.roomevents;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.ItemTask;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.Direction8;
import cappo.game.player.PlayerData;
import cappo.game.roomeffects.special.SkatesEffect;
import cappo.game.roomeffects.special.SwimEffect;
import cappo.game.roomeffects.special.UserSpecialEffect;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.Square;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.floor.RollerItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.LiveEntity;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.game.roomengine.wired.WiredManager;
import cappo.game.roomgames.RoomGamePlayer;
import cappo.game.roomgames.banzai.BattleBanzai;
import cappo.game.roomgames.banzai.utils.PuckBanzai;
import cappo.game.roomgames.banzai.utils.TileBanzaiWork;
import cappo.game.roomgames.football.FootBall;
import cappo.game.roomgames.football.utils.BallonFootBall;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User_WALK
  extends Event
{
  private static final int MAX_STEEPS = 25;
  private final LiveEntity liveEntity;
  private List<Square> walkPath;
  private int walkX;
  private int walkY;
  private int nextX;
  private int nextY;
  private int nextXY;
  private float nextZ;
  private boolean doSteep;
  private boolean findPath;
  public boolean isWalking;
  
  public User_WALK(LiveEntity user)
  {
    this.liveEntity = user;
  }
  
  private Square findBestPassThrough(RoomTask room, Set<Square> opened, Square start, Square goal)
  {
    // Greedy best-first step: among the opened (adjacent) squares, pick the
    // walkable one with the lowest cost toward the goal. The decompiled source
    // had mangled this into two nested if-checks with empty bodies, so for a
    // normal avatar (allowOverride == false) `best` was never assigned and the
    // method always returned null -- which made findBestPath return null, kept
    // walkPath null, and caused run() to call stopWalk() on the first tick, so
    // the avatar never moved. Reconstructed to match canWalk() (see run()).
    Square best = null;
    for (Square square : opened)
    {
      if (square.xy == goal.xy) {
        return goal;
      }
      if (!this.liveEntity.allowOverride)
      {
        if (!room.squareFlag.have(square.xy, 4)) {
          continue;
        }
        if ((!room.roomData.haveFlag(8)) && (room.squareHasUsers(square.xy))) {
          continue;
        }
      }
      if ((best == null) || (square.getLocalCost(start, goal) < best.getLocalCost(start, goal))) {
        best = square;
      }
    }
    return best;
  }
  
  private List<Square> findBestPath(RoomTask room, Square start, Square goal)
  {
    List<Square> bestList = new ArrayList(25);
    
    boolean disableDiagonal = false;
    if (this.liveEntity.entityType == 1)
    {
      Avatar avatar = (Avatar)this.liveEntity;
      disableDiagonal = avatar.cn.haveFlag(64);
    }
    Square best = start;
    for (int i = 0; i < 25; i++)
    {
      Square newBest = findBestPassThrough(room, disableDiagonal ? best.adjacenciesNoDiagonal : best.adjacencies, start, goal);
      if (newBest == null) {
        return null;
      }
      bestList.add(newBest);
      best = newBest;
      if (best.xy == goal.xy) {
        return bestList;
      }
    }
    return null;
  }
  
  private void checkEffects(RoomTask room, Avatar avatar)
  {
    if ((!avatar.HaveStatus("sit")) && (!avatar.HaveStatus("lay")))
    {
      if (room.squareFlag.eventHave(this.nextXY, 1))
      {
        FloorItem top = (FloorItem)room.topFloorItems.get(Integer.valueOf(this.nextXY));
        if (top.baseItem.interactorType == Interactor.InteractorType.fbgate) {
          FootBall.togglePlayer(avatar, (short)1);
        } else if (top.baseItem.interactorType == Interactor.InteractorType.banzaigatered) {
          BattleBanzai.togglePlayer(avatar, (short)1);
        } else if (top.baseItem.interactorType == Interactor.InteractorType.banzaigategreen) {
          BattleBanzai.togglePlayer(avatar, (short)2);
        } else if (top.baseItem.interactorType == Interactor.InteractorType.banzaigateblue) {
          BattleBanzai.togglePlayer(avatar, (short)3);
        } else if (top.baseItem.interactorType == Interactor.InteractorType.banzaigateyellow) {
          BattleBanzai.togglePlayer(avatar, (short)4);
        }
        return;
      }
      if (avatar.roomGamePlayer != null) {
        return;
      }
      if (room.squareFlag.eventHave(this.nextXY, 4))
      {
        short effectId = (short)(54 + avatar.cn.getPlayerData().sex);
        if ((avatar.userSpecialEffect == null) || (avatar.userSpecialEffect.effectId != effectId)) {
          avatar.userSpecialEffect = new SkatesEffect(avatar, effectId);
        }
        return;
      }
      if (room.squareFlag.eventHave(this.nextXY, 128))
      {
        GenericFloorItem top = (GenericFloorItem)room.topFloorItems.get(Integer.valueOf(this.nextXY));
        if (top.baseItem.interactorType == Interactor.InteractorType.lowpool)
        {
          if ((avatar.userSpecialEffect == null) || (avatar.userSpecialEffect.effectId != 30)) {
            avatar.userSpecialEffect = new SwimEffect(avatar, (short)30);
          }
        }
        else if (top.baseItem.interactorType == Interactor.InteractorType.pool)
        {
          if ((avatar.userSpecialEffect == null) || (avatar.userSpecialEffect.effectId != 29)) {
            avatar.userSpecialEffect = new SwimEffect(avatar, (short)29);
          }
        }
        else if ((top.baseItem.interactorType == Interactor.InteractorType.haloweenpool) && (
          (avatar.userSpecialEffect == null) || (avatar.userSpecialEffect.effectId != 37))) {
          avatar.userSpecialEffect = new SwimEffect(avatar, (short)37);
        }
        return;
      }
    }
    if (avatar.roomGamePlayer != null) {
      return;
    }
    if (avatar.userSpecialEffect != null) {
      avatar.userSpecialEffect.stopEffect();
    }
  }
  
  private void findWalkPath(RoomTask room)
  {
    if ((this.liveEntity.entityType != 1) && 
      (this.walkX == room.model.doorX) && (this.walkY == room.model.doorY)) {
      return;
    }
    int walkXY = this.walkX + this.walkY * room.model.widthX;
    if (!room.canWalk(this.liveEntity, walkXY, true)) {
      return;
    }
    if (!room.validTile(this.liveEntity.xy)) {
      return;
    }
    Square start = room.model.getSquare(this.liveEntity.xy);
    Square goal = room.model.getSquare(walkXY);
    
    this.walkPath = findBestPath(room, start, goal);
  }
  
  public boolean doWalkSteep(RoomTask room)
  {
    int type;
    if (this.liveEntity.entityType == 1)
    {
      Avatar avatar = (Avatar)this.liveEntity;
      if (room.squareFlag.eventHave(this.nextXY, 512)) {
        room.wiredManager.parseWiredWalksOffFurni(avatar.cn, this.nextXY);
      }
      if (room.squareFlag.eventHave(this.nextXY, 256)) {
        room.wiredManager.parseWiredWalksOnFurni(avatar.cn, this.nextXY);
      }
      if (!this.isWalking) {
        return false;
      }
      if ((avatar.roomGamePlayer != null) && 
        (room.squareFlag.eventHave(this.nextXY, 8)))
      {
        GenericFloorItem top = (GenericFloorItem)room.topFloorItems.get(Integer.valueOf(this.nextXY));
        type = avatar.roomGamePlayer.team;
        if (top.getIntData() != 3 * type + 2) {
          TileBanzaiWork.doWork(top, type, room);
        }
      }
    }
    this.liveEntity.x = this.nextX;
    this.liveEntity.y = this.nextY;
    this.liveEntity.xy = this.nextXY;
    if (this.liveEntity.ridingEntity != null)
    {
      this.liveEntity.ridingEntity.x = this.nextX;
      this.liveEntity.ridingEntity.y = this.nextY;
      this.liveEntity.ridingEntity.xy = this.nextXY;
      if (this.liveEntity.entityType == 2)
      {
        this.liveEntity.ridingEntity.z = (this.nextZ + 1.0F);
        this.liveEntity.z = this.nextZ;
      }
      else
      {
        this.liveEntity.ridingEntity.z = this.nextZ;
        this.liveEntity.z = (this.nextZ + 1.0F);
      }
      room.userUpdateNeeded(this.liveEntity.ridingEntity);
      room.userUpdateNeeded(this.liveEntity);
    }
    else
    {
      this.liveEntity.z = this.nextZ;
      
      room.updateUserStatus(this.liveEntity, true);
      room.userUpdateNeeded(this.liveEntity);
    }
    if ((this.walkX == this.liveEntity.x) && (this.liveEntity.y == this.walkY))
    {
      this.isWalking = false;
      if ((this.walkX == room.model.doorX) && (this.walkY == room.model.doorY))
      {
        if (this.liveEntity.entityType == 1)
        {
          Avatar user = (Avatar)this.liveEntity;
          room.removeUserFromRoom(user.cn, true, false);
        }
      }
      else if (room.squareFlag.eventHave(this.liveEntity.xy, 2))
      {
        Map<Integer, FloorItem> squareItems = (Map)room.mapFloorItems.get(Integer.valueOf(this.liveEntity.xy));
        if (squareItems != null) {
          for (FloorItem roller : squareItems.values()) {
            if (roller.baseItem.interactorType == Interactor.InteractorType.roller)
            {
              room.rollers.put(Integer.valueOf(roller.itemId), (RollerItem)roller);
              break;
            }
          }
        }
      }
    }
    return this.isWalking;
  }
  
  public void run(RoomTask room)
  {
    if (!this.isWalking)
    {
      this.walkPath = null;
      room.entityWalk(this.nextXY, this.liveEntity, false);
      return;
    }
    if (this.doSteep)
    {
      this.doSteep = false;
      if (!doWalkSteep(room))
      {
        this.walkPath = null;
        stopWalk(room);
        return;
      }
    }
    if (this.findPath)
    {
      this.findPath = false;
      this.walkPath = null;
      findWalkPath(room);
    }
    if ((this.walkPath == null) || (this.walkPath.isEmpty()))
    {
      stopWalk(room);
      return;
    }
    Square nextStep = (Square)this.walkPath.remove(0);
    if (!room.canWalk(this.liveEntity, nextStep.xy, (this.walkX == nextStep.x) && (this.walkY == nextStep.y)))
    {
      if (this.walkPath.isEmpty())
      {
        stopWalk(room);
        return;
      }
      this.walkPath = null;
      findWalkPath(room);
      if ((this.walkPath == null) || (this.walkPath.isEmpty()))
      {
        stopWalk(room);
        return;
      }
      nextStep = (Square)this.walkPath.remove(0);
    }
    Direction8 newRot = Direction8.getRot(this.liveEntity.x, this.liveEntity.y, nextStep.x, nextStep.y);
    if (this.liveEntity.entityType == 1)
    {
      Avatar avatar = (Avatar)this.liveEntity;
      if (avatar.cn.haveFlag(32)) {
        newRot = newRot.rotateDirection180Degrees();
      }
    }
    this.nextZ = room.calculateZ(nextStep.xy);
    this.nextX = nextStep.x;
    this.nextY = nextStep.y;
    this.nextXY = nextStep.xy;
    
    room.entityWalk(this.nextXY, this.liveEntity, true);
    room.entityWalk(this.liveEntity.xy, this.liveEntity, false);
    
    this.liveEntity.RotBody = newRot;
    this.liveEntity.RotHead = newRot;
    if (this.liveEntity.ridingEntity != null)
    {
      this.liveEntity.ridingEntity.RotBody = newRot;
      this.liveEntity.ridingEntity.RotHead = newRot;
      if (this.liveEntity.entityType == 2)
      {
        this.liveEntity.ridingEntity.setStatus("mv", this.nextX + "," + this.nextY + "," + Float.toString(this.nextZ + 1.0F).replace(',', '.'));
        this.liveEntity.setStatus("mv", this.nextX + "," + this.nextY + "," + Float.toString(this.nextZ).replace(',', '.'));
      }
      else
      {
        this.liveEntity.ridingEntity.setStatus("mv", this.nextX + "," + this.nextY + "," + Float.toString(this.nextZ).replace(',', '.'));
        this.liveEntity.setStatus("mv", this.nextX + "," + this.nextY + "," + Float.toString(this.nextZ + 1.0F).replace(',', '.'));
      }
    }
    else
    {
      this.liveEntity.setStatus("mv", this.nextX + "," + this.nextY + "," + Float.toString(this.nextZ).replace(',', '.'));
      if (this.liveEntity.entityType == 1) {
        checkEffects(room, (Avatar)this.liveEntity);
      }
    }
    this.doSteep = true;
    if (this.liveEntity.entityType == 1)
    {
      Avatar avatar = (Avatar)this.liveEntity;
      if (room.squareFlag.eventHave(this.nextXY, 16))
      {
        GenericFloorItem puckItem = (GenericFloorItem)room.topFloorItems.get(Integer.valueOf(this.nextXY));
        ItemTask.addTask(new PuckBanzai(puckItem, avatar, (this.walkX == nextStep.x) && (this.walkY == nextStep.y)), 50, 0);
      }
      if (room.squareFlag.eventHave(this.nextXY, 32))
      {
        GenericFloorItem ballItem = (GenericFloorItem)room.topFloorItems.get(Integer.valueOf(this.nextXY));
        ItemTask.addTask(new BallonFootBall(ballItem, avatar, (this.walkX == nextStep.x) && (this.walkY == nextStep.y)), 50, 0);
      }
    }
    this.Ticks = 0;
  }
  
  private void stopWalk(RoomTask room)
  {
    this.isWalking = false;
    
    room.entityWalk(this.nextXY, this.liveEntity, false);
    room.entityWalk(this.liveEntity.xy, this.liveEntity, true);
    
    this.liveEntity.allowOverride = false;
    if (this.liveEntity.Status.contains("mv")) {
      this.liveEntity.setStatus("", "");
    }
    if ((this.liveEntity.ridingEntity != null) && 
      (this.liveEntity.ridingEntity.Status.contains("mv"))) {
      this.liveEntity.ridingEntity.setStatus("", "");
    }
  }
  
  public void walk(RoomTask room, int x, int y)
  {
    this.walkX = x;
    this.walkY = y;
    
    this.findPath = true;
    if (!this.isWalking)
    {
      this.isWalking = true;
      room.addUserEvent(this, 0);
    }
  }
  
  public int getWalkX()
  {
    return this.walkX;
  }
  
  public void setWalkX(int walkX)
  {
    this.walkX = walkX;
  }
  
  public int getWalkY()
  {
    return this.walkY;
  }
  
  public void setWalkY(int walkY)
  {
    this.walkY = walkY;
  }
  
  public int getNextXY()
  {
    return this.nextXY;
  }
  
  public void setNextXY(int nextXY)
  {
    this.nextXY = nextXY;
  }
  
  public float getNextZ()
  {
    return this.nextZ;
  }
  
  public void setNextZ(float nextZ)
  {
    this.nextZ = nextZ;
  }
  
  public int getNextY()
  {
    return this.nextY;
  }
  
  public int setNextY(int nextY)
  {
    this.nextY = nextY;
    return nextY;
  }
  
  public int getNextX()
  {
    return this.nextX;
  }
  
  public int setNextX(int nextX)
  {
    this.nextX = nextX;
    return nextX;
  }
}
