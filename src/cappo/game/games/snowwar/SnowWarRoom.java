package cappo.game.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.games.snowwar.gameevents.Event;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SnowWarRoom
  extends SynchronizedGameStage
{
  public int[] TeamScore = new int[SnowWar.TEAMS.length];
  public int checksum;
  public int roomId;
  public int Result;
  public int STATUS;
  public int TimeToStart;
  public int Turn;
  public int Winner;
  public String Name;
  public String Owner;
  public boolean LobbyFull;
  public SnowWarArenaBase ArenaType;
  public HumanGameObject MostHits;
  public HumanGameObject MostKills;
  public SnowWarGameStage map;
  public final Map<Integer, Map<Integer, HumanGameObject>> TeamPlayers = new ConcurrentHashMap(SnowWar.TEAMS.length);
  public final Map<Integer, HumanGameObject> players = new ConcurrentHashMap(10);
  public final List<Event> gameEvents = new ArrayList(50);
  public List<Channel> fullGameStatusQueue;
  private Map<Integer, HumanGameObject> stageLoadedPlayers;
  
  public SnowWarRoom(int id)
  {
    this.ArenaType = SnowWar.ArenaTypes[cappo.game.collections.Utils.GetRandomNumber(0, SnowWar.ArenaTypes.length - 1)];
    this.roomId = id;
    this.Name = ("SnowStorm level " + this.ArenaType.ArenaType);
    this.map = new SnowWarGameStage();
    this.map.initialize(this.ArenaType);
    for (int TeamId : SnowWar.TEAMS) {
      this.TeamPlayers.put(Integer.valueOf(TeamId), new ConcurrentHashMap());
    }
  }
  
  public void broadcast(MessageWriter Message)
  {
    for (HumanGameObject player : this.players.values()) {
      if (player.currentSnowWar != null) {
        QueueWriter.writeAndFlush(player.cn.socket, Message);
      }
    }
  }
  
  public Collection<HumanGameObject> getStageLoadedPlayers()
  {
    if (this.stageLoadedPlayers == null) {
      return null;
    }
    Collection<HumanGameObject> result = this.stageLoadedPlayers.values();
    this.stageLoadedPlayers = null;
    
    return result;
  }
  
  public void stageLoaded(HumanGameObject humanObject)
  {
    if (this.stageLoadedPlayers == null) {
      this.stageLoadedPlayers = new ConcurrentHashMap();
    }
    this.stageLoadedPlayers.put(Integer.valueOf(humanObject.objectId), humanObject);
    humanObject.stageLoaded = true;
  }
}


