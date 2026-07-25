package cappo.game.games.snowwar;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.engine.threadpools.SnowWarTask;
import cappo.game.games.snowwar.gameevents.PlayerLeft;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.player.PlayerData;
import cappo.game.player.SnowWarPlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.composers.games.snowwar.ArenaEnteredComposer;
import cappo.protocol.messages.composers.games.snowwar.EnterArenaComposer;
import cappo.protocol.messages.composers.games.snowwar.GameCreatedComposer;
import cappo.protocol.messages.composers.games.snowwar.GameLongDataComposer;
import cappo.protocol.messages.composers.games.snowwar.GameStartedComposer;
import cappo.protocol.messages.composers.games.snowwar.InArenaQueueComposer;
import cappo.protocol.messages.composers.games.snowwar.StageLoadComposer;
import cappo.protocol.messages.composers.games.snowwar.StartCounterComposer;
import cappo.protocol.messages.composers.games.snowwar.UserJoinedGameComposer;
import cappo.protocol.messages.composers.games.snowwar.UserLeftGameComposer;
import cappo.protocol.messages.composers.room.session.YouArePlayingGameComposer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SnowPlayerQueue
{
  private static int roomCounter;
  public static final Map<Integer, RoomQueue> roomQueue = new ConcurrentHashMap(100);
  
  public static void addPlayerInQueue(Connection cn)
  {
    PlayerData playerData = cn.playerData;
    
    RoomQueue pickRoom = null;
    if (roomQueue.isEmpty())
    {
      pickRoom = new RoomQueue(new SnowWarRoom(++roomCounter));
      roomQueue.put(Integer.valueOf(pickRoom.room.roomId), pickRoom);
    }
    else
    {
      for (RoomQueue room : roomQueue.values()) {
        if (room.players.size() < 10)
        {
          pickRoom = room;
          break;
        }
      }
      if (pickRoom == null)
      {
        pickRoom = new RoomQueue(new SnowWarRoom(++roomCounter));
        roomQueue.put(Integer.valueOf(pickRoom.room.roomId), pickRoom);
      }
    }
    if (pickRoom.players.isEmpty()) {
      pickRoom.room.Owner = playerData.userName;
    }
    cn.snowWarPlayerData.setHumanObject(new HumanGameObject(pickRoom.room, 0));
    cn.snowWarPlayerData.humanObject.status = 0;
    cn.snowWarPlayerData.setRoom(pickRoom.room);
    
    pickRoom.broadcast(UserJoinedGameComposer.compose(cn));
    
    pickRoom.players.put(Integer.valueOf(playerData.userId), cn);
    if (pickRoom.room.Owner.equals(playerData.userName)) {
      QueueWriter.writeAndFlush(cn.socket, GameCreatedComposer.compose(pickRoom));
    } else {
      QueueWriter.writeAndFlush(cn.socket, GameLongDataComposer.compose(pickRoom));
    }
    if (pickRoom.players.size() >= 4) {
      startLoading(pickRoom);
    }
  }
  
  public static void playerExit(SnowWarRoom room, HumanGameObject playerObject)
  {
    RoomQueue queue = (RoomQueue)roomQueue.get(Integer.valueOf(room.roomId));
    if (queue == null)
    {
      room.players.remove(Integer.valueOf(playerObject.userId));
      ((Map)room.TeamPlayers.get(Integer.valueOf(playerObject.team))).remove(Integer.valueOf(playerObject.userId));
      if (room.STATUS == 5)
      {
        synchronized (room.gameEvents)
        {
          room.gameEvents.add(new PlayerLeft(playerObject));
        }
        return;
      }
      room.broadcast(UserLeftGameComposer.compose(playerObject.userId));
    }
    else
    {
      queue.broadcast(UserLeftGameComposer.compose(playerObject.userId));
      queue.players.remove(Integer.valueOf(playerObject.userId));
    }
    playerObject.cleanData();
  }
  
  public static void roomLoaded(SnowWarRoom room)
  {
    RoomQueue queue = (RoomQueue)roomQueue.remove(Integer.valueOf(room.roomId));
    if (queue == null) {
      return;
    }
    int pickTeam = 0;
    for (Connection cn : queue.players.values())
    {
      room.players.put(Integer.valueOf(cn.playerData.userId), cn.snowWarPlayerData.humanObject);
      int team = 1 + ++pickTeam % SnowWar.TEAMS.length;
      cn.snowWarPlayerData.humanObject.team = team;
      ((Map)room.TeamPlayers.get(Integer.valueOf(team))).put(Integer.valueOf(cn.playerData.userId), cn.snowWarPlayerData.humanObject);
    }
    queue.broadcast(GameStartedComposer.compose(queue));
    queue.broadcast(InArenaQueueComposer.compose(1));
    queue.broadcast(YouArePlayingGameComposer.compose(true));
    room.broadcast(EnterArenaComposer.compose(room));
    for (HumanGameObject player : room.players.values())
    {
      player.status = 1;
      if (player.cn.avatar != null) {
        player.cn.avatar.room.removeUserFromRoom(player.cn, false, false);
      }
      room.broadcast(ArenaEnteredComposer.compose(player));
    }
    room.broadcast(StageLoadComposer.compose());
  }
  
  private static void startLoading(RoomQueue queue)
  {
    SnowWarRoom room = queue.room;
    if (room.STATUS == 1) {
      return;
    }
    room.TimeToStart = 15;
    room.STATUS = 1;
    
    queue.broadcast(StartCounterComposer.compose(room.TimeToStart));
    
    SnowWarTask.addTask(new SnowWarTask(room), 0, 1000);
  }
}


