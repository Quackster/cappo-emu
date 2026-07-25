package cappo.protocol.messages.events.room.engine;

import cappo.engine.logging.Log;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.bots.RentalBot;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.game.polls.Poll;
import cappo.game.polls.PollManager;
import cappo.game.roomeffects.UserEffect;
import cappo.game.roomeffects.special.UserSpecialEffect;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomEvent;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.entity.item.floor.wired.trigger.WiredTriggerBase;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.RentalBotEntity;
import cappo.game.roomengine.settings.ControllerLevels;
import cappo.game.roomengine.wired.WiredManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.EventComposer;
import cappo.protocol.messages.composers.navigator.FlatAccessDeniedComposer;
import cappo.protocol.messages.composers.poll.PollOfferMessageComposer;
import cappo.protocol.messages.composers.room.action.UserAsleepComposer;
import cappo.protocol.messages.composers.room.action.UserDanceComposer;
import cappo.protocol.messages.composers.room.engine.FloorHeightMapComposer;
import cappo.protocol.messages.composers.room.engine.HeightMapComposer;
import cappo.protocol.messages.composers.room.engine.ItemsComposer;
import cappo.protocol.messages.composers.room.engine.ObjectsComposer;
import cappo.protocol.messages.composers.room.engine.PublicRoomObjectsMessageParser;
import cappo.protocol.messages.composers.room.engine.RoomEntryInfoComposer;
import cappo.protocol.messages.composers.room.engine.RoomVisualizationSettingsComposer;
import cappo.protocol.messages.composers.room.engine.UserUpdateComposer;
import cappo.protocol.messages.composers.room.engine.UsersComposer;
import cappo.protocol.messages.composers.room.session.CloseConnectionComposer;
import java.util.Map;

public class GetRoomEntryDataParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (cn.avatarData.LoadingRoom == 0) {
      return;
    }
    RoomData roomData = RoomManager.getRoom(cn.avatarData.LoadingRoom);
    
    cn.avatarData.LoadingRoom = 0;
    if (roomData == null) {
      return;
    }
    RoomTask room = roomData.room;
    if (room.model == null)
    {
      QueueWriter.write(cn.socket, FlatAccessDeniedComposer.compose(2, ""));
      QueueWriter.write(cn.socket, CloseConnectionComposer.compose());
      return;
    }
    PlayerData playerData = cn.getPlayerData();
    
    int controllerLevel = ControllerLevels.getLevel(playerData, roomData, room);
    if (controllerLevel < 4)
    {
      if (room.userCount >= roomData.usersMax)
      {
        QueueWriter.write(cn.socket, FlatAccessDeniedComposer.compose(1, ""));
        QueueWriter.write(cn.socket, CloseConnectionComposer.compose());
        return;
      }
      if ((room.userIsBanned(playerData.userId)) && 
        (!room.hasBanExpired(playerData.userId)))
      {
        QueueWriter.write(cn.socket, FlatAccessDeniedComposer.compose(4, ""));
        QueueWriter.write(cn.socket, CloseConnectionComposer.compose());
        return;
      }
    }
    QueueWriter.write(cn.socket, HeightMapComposer.compose(room));
    QueueWriter.write(cn.socket, FloorHeightMapComposer.compose(room.model));
    

    boolean clean = false;
    while (!clean)
    {
      clean = true;
      for (Avatar User : room.userList.values()) {
        if ((User.cn == null) || (User.cn.getPlayerData() == null))
        {
          Log.printLog("PROBLEM: User List is bugged, userid=" + User.id);
          room.userList.remove(Integer.valueOf(User.id));
          clean = false;
          break;
        }
      }
    }
    QueueWriter.write(cn.socket, UsersComposer.compose(room.userList.values(), room.petList.values(), room.rentalBotList.values()));
    QueueWriter.write(cn.socket, PublicRoomObjectsMessageParser.compose());
    QueueWriter.write(cn.socket, ObjectsComposer.compose(room.FloorItems.values()));
    QueueWriter.write(cn.socket, ItemsComposer.compose(room.WallItems.values()));
    
    room.addUserToRoom(cn);
    
    QueueWriter.write(cn.socket, RoomVisualizationSettingsComposer.compose(Boolean.valueOf(roomData.haveFlag(16)), roomData.wallAnchor, roomData.floorAnchor));
    QueueWriter.write(cn.socket, RoomEntryInfoComposer.compose(Boolean.valueOf(true), roomData.roomId, Boolean.valueOf(cn.avatar.controllerLevel >= 4)));
    if (roomData.event != null) {
      QueueWriter.write(cn.socket, EventComposer.compose(cn.playerData.userId, cn.playerData.userName, roomData.roomId, roomData.event.category, roomData.event.name, roomData.event.description, roomData.event.startTime));
    } else {
      QueueWriter.write(cn.socket, EventComposer.compose());
    }
    QueueWriter.write(cn.socket, UserUpdateComposer.compose(room.userList.values(), room.petList.values()));
    for (Avatar User : room.userList.values())
    {
      if (User.IsDancing) {
        QueueWriter.write(cn.socket, UserDanceComposer.compose(User.virtualId, User.DanceId));
      }
      if (User.IsAsleep) {
        QueueWriter.write(cn.socket, UserAsleepComposer.compose(User.virtualId, Boolean.valueOf(User.IsAsleep)));
      }
      if (User.userSpecialEffect != null) {
        User.userSpecialEffect.startEffect(cn.socket);
      } else if (User.userEffect != null) {
        User.userEffect.startEffect(cn.socket);
      }
    }
    for (RentalBotEntity bot : room.rentalBotList.values()) {
      if (bot.botData.danceEnabled) {
        QueueWriter.write(cn.socket, UserDanceComposer.compose(bot.virtualId, 1));
      }
    }
    Poll poll = (Poll)PollManager.roomPolls.get(Integer.valueOf(room.roomId));
    if (poll != null) {
      QueueWriter.write(cn.socket, PollOfferMessageComposer.compose(poll));
    }
    WiredTriggerBase.launchTriggers(room.wiredManager.triggersEntersRoom, cn, playerData.userName);
    WiredTriggerBase.launchTriggers(room.wiredManager.triggersTimers, null, null);
  }
}


