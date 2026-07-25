package cappo.game.roomengine.itemInteractor;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.floor.OutFitItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.composers.room.engine.UserChangeComposer;

public class InteractorOutfit
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item) {}
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem item, int Request, boolean UserHasRights)
  {
    if (User == null) {
      return;
    }
    PlayerData playerData = User.getPlayerData();
    
    ((OutFitItem)item).generateLook(playerData);
    
    QueueWriter.writeAndFlush(User.socket, UserChangeComposer.compose(-1, playerData.avatarLook.toString(), playerData.sex, playerData.motto, playerData.AchievementsScore));
    room.sendMessage(UserChangeComposer.compose(User.avatar.virtualId, playerData.avatarLook.toString(), playerData.sex, playerData.motto, playerData.AchievementsScore));
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
}


