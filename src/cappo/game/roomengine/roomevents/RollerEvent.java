package cappo.game.roomengine.roomevents;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData;

import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.Direction8;
import cappo.game.rollers.RollerMoveDataEntity;
import cappo.game.rollers.RollerMoveDataObject;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.RollerItem;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData.AffectedTile;
import cappo.game.roomengine.entity.live.LiveEntity;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.composers.room.engine.SlideObjectBundleComposer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RollerEvent
{
  public static List<FloorItem> cleanRollerTile(RoomTask room, int xy, float z)
  {
    List<FloorItem> modified = new ArrayList();
    Map<Integer, FloorItem> squareItems = (Map)room.mapFloorItems.get(Integer.valueOf(xy));
    if (squareItems != null) {
      for (FloorItem floorItem : squareItems.values()) {
        if (floorItem.getZ() > z)
        {
          modified.add(floorItem);
          for (RoomFloorItemData.AffectedTile Tile : floorItem.getAffectedTiles()) {
            if (room.validTile(Tile.xy)) {
              room.generateSquare(Tile.xy, floorItem, false, false);
            }
          }
        }
      }
    }
    return modified;
  }
  
  private static void execRoller(RoomTask room, RollerItem roller, int mainId)
  {
    if (roller.getRoomId() != room.roomId) {
      return;
    }
    int fromX = roller.getX();
    int fromY = roller.getY();
    int nextX = fromX + roller.getDir().getDiffX();
    int nextY = fromY + roller.getDir().getDiffY();
    int nextXY = nextX + nextY * room.model.widthX;
    if (!room.validTile(nextXY)) {
      return;
    }
    roller.status = 1;
    


    List<FloorItem> modified = cleanRollerTile(room, roller.getXy(), roller.getZ());
    RollerItem rl;
    if (room.squareFlag.eventHave(nextXY, 2))
    {
      Map<Integer, FloorItem> squareItems = (Map)room.mapFloorItems.get(Integer.valueOf(nextXY));
      for (FloorItem r : squareItems.values()) {
        if (r.baseItem.interactorType == Interactor.InteractorType.roller)
        {
          rl = (RollerItem)r;
          if (rl.status == 0)
          {
            execRoller(room, rl, mainId);
            break;
          }
          if (rl.status != 1) {
            break;
          }
          break;
        }
      }
    }
    float delta = ((Float)room.squareAbsoluteHeight.get(Integer.valueOf(nextXY))).floatValue() - (roller.getZ() + roller.baseItem.Height);
    
    List<RollerMoveDataObject> stackedItems = new ArrayList();
    for (FloorItem stacked : modified)
    {
      List<RoomFloorItemData.AffectedTile> Points = stacked.getAffectedTiles(nextXY, stacked.getDir());
      if (!room.canPlace(stacked, Points, nextXY, false)) {
        for (RoomFloorItemData.AffectedTile Tile : stacked.getAffectedTiles()) {
          if (room.validTile(Tile.xy)) {
            room.generateSquare(Tile.xy, stacked, true, true);
          }
        }
      } else {
        stackedItems.add(new RollerMoveDataObject(stacked));
      }
    }
    for (RollerMoveDataObject roolerMove : stackedItems)
    {
      FloorItem stacked = roolerMove.item;
      List<RoomFloorItemData.AffectedTile> Points = stacked.getAffectedTiles(nextXY, stacked.getDir());
      
      room.moveObject2(stacked, Points, nextXY, stacked.getDir());
      
      stacked.setMysqlState(1);
      stacked.finishPlace(null, Points, false);
    }
    RollerMoveDataEntity moveDataEntity = null;
    LiveEntity ent = null;
    if (((room.squareFlag.have(nextXY, 4)) || (room.squareFlag.have(nextXY, 8))) && 
      (!room.squareHasUsers(nextXY)))
    {
      Map<Short, LiveEntity> users = (Map)room.usersMatrix.get(Integer.valueOf(roller.getXy()));
      if (users != null) {
        for (LiveEntity entity : users.values()) {
          if (!entity.evtWalk.isWalking)
          {
            ent = entity;
            break;
          }
        }
      }
      if (ent != null)
      {
        moveDataEntity = new RollerMoveDataEntity(ent, 2);
        
        room.entityWalk(ent.xy, ent, false);
        






        ent.SetPos(nextX, nextY, ent.z + delta);
        
        room.entityWalk(ent.xy, ent, true);
      }
    }
    roller.status = 2;
    if ((stackedItems.isEmpty()) && (ent == null)) {
      return;
    }
    room.sendMessage(SlideObjectBundleComposer.compose(roller, nextX, nextY, stackedItems, moveDataEntity));
  }
  
  public static void run(RoomTask room, Map<Integer, RollerItem> rollers)
  {
    for (RollerItem roller : rollers.values()) {
      if (roller.status == 0) {
        execRoller(room, roller, roller.itemId);
      }
    }
    for (RollerItem roller : rollers.values()) {
      roller.status = 0;
    }
  }
}
