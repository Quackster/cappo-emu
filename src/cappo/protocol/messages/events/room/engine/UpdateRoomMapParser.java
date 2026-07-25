package cappo.protocol.messages.events.room.engine;

import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.gamemap.CustomGameMap;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.protocol.messages.IncomingMessageEvent;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class UpdateRoomMapParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask roomTask = avatar.room;
    if ((roomTask == null) || (roomTask.roomData.roomOwnerId != Main.playerData.userId)) {
      return;
    }
    String newModel = "custom_" + roomTask.roomId;
    
    CustomGameMap model = new CustomGameMap(newModel, 
      roomTask.model.doorX, 
      roomTask.model.doorY, 
      roomTask.model.doorZ, 
      roomTask.model.DoorOrientation, false);
    CustomGameMap customModel;
    try
    {
      String map = Main.currentPacket.readString();
      model.generateModel(map.split("\r"));
      if ((roomTask.model instanceof CustomGameMap))
      {
        customModel = (CustomGameMap)roomTask.model;
        model.baseName = customModel.baseName;
        

        model.mysqlAction = 2;
      }
      else
      {
        model.baseName = roomTask.model.modelName;
        

        model.mysqlAction = 1;
      }
    }
    catch (Exception ex)
    {
      Log.printException("RoomManager", ex);
      return;
    }
    roomTask.model = model;
    roomTask.roomData.model = model.modelName;
    

    roomTask.future.cancel(false);
    for (Avatar user : roomTask.userList.values()) {
      roomTask.removeUserFromRoom(user.cn, true, false);
    }
    RoomManager.setInactive(roomTask.roomData);
    roomTask.updateMysqlData();
  }
}


