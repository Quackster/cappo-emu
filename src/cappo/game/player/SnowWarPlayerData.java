package cappo.game.player;

import cappo.game.games.snowwar.PlayerTile;
import cappo.game.games.snowwar.SnowPlayerQueue;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.SynchronizedGameStage;
import cappo.game.games.snowwar.Tile;
import cappo.game.games.snowwar.gameevents.BallThrowToHuman;
import cappo.game.games.snowwar.gameevents.BallThrowToPosition;
import cappo.game.games.snowwar.gameevents.CreateSnowBall;
import cappo.game.games.snowwar.gameevents.MakeSnowBall;
import cappo.game.games.snowwar.gameevents.UserMove;
import cappo.game.games.snowwar.gameobjects.GameItemObject;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.games.snowwar.gameobjects.SnowBallGameObject;
import java.util.List;
import java.util.Map;

public class SnowWarPlayerData
  extends GamePlayerData
{
  public PlayerData player;
  public SnowWarRoom currentSnowWar;
  public HumanGameObject humanObject;
  public int snowLevel;
  public int PointsNeed;
  
  public SnowWarPlayerData(PlayerData playerData)
  {
    this.player = playerData;
    this.snowLevel = 1;
    this.rank = 1;
    this.score = 10;
    this.PointsNeed = 100;
  }
  
  public void setHumanObject(HumanGameObject humanGameObject)
  {
    if (humanGameObject == null)
    {
      this.humanObject.snowWarPlayer = null;
      this.humanObject.cn = null;
      this.humanObject.userId = 0;
      this.humanObject = null;
    }
    else
    {
      this.humanObject = humanGameObject;
      this.humanObject.snowWarPlayer = this;
      
      this.humanObject.cn = this.player.connection;
      this.humanObject.userId = this.player.userId;
      this.humanObject.userName = this.player.userName;
      this.humanObject.look = this.player.avatarLook.toString();
      this.humanObject.motto = this.player.motto;
      this.humanObject.sex = this.player.sex;
    }
  }
  
  public void setRoom(SnowWarRoom room)
  {
    this.currentSnowWar = room;
  }
  
  public void userLeft()
  {
    if ((this.humanObject != null) && (this.currentSnowWar != null)) {
      SnowPlayerQueue.playerExit(this.currentSnowWar, this.humanObject);
    }
  }
  
  public void makeSnowBall()
  {
    synchronized (this.currentSnowWar.gameEvents)
    {
      this.currentSnowWar.gameEvents.add(new MakeSnowBall(this.humanObject));
    }
  }
  
  public void playerMove(int x, int y)
  {
    synchronized (this.currentSnowWar.gameEvents)
    {
      this.currentSnowWar.gameEvents.add(new UserMove(this.humanObject, x, y));
    }
  }
  
  public void throwSnowballFlood(int destX, int destY)
  {
    SnowBallGameObject ball = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp17_14 = this.currentSnowWar; int tmp21_18 = tmp17_14.objectIdCounter;tmp17_14.objectIdCounter = (tmp21_18 + 1);ball.objectId = tmp21_18;
    SnowBallGameObject ball1 = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp49_46 = this.currentSnowWar; int tmp53_50 = tmp49_46.objectIdCounter;tmp49_46.objectIdCounter = (tmp53_50 + 1);ball1.objectId = tmp53_50;
    SnowBallGameObject ball2 = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp81_78 = this.currentSnowWar; int tmp85_82 = tmp81_78.objectIdCounter;tmp81_78.objectIdCounter = (tmp85_82 + 1);ball2.objectId = tmp85_82;
    SnowBallGameObject ball3 = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp113_110 = this.currentSnowWar; int tmp117_114 = tmp113_110.objectIdCounter;tmp113_110.objectIdCounter = (tmp117_114 + 1);ball3.objectId = tmp117_114;
    SnowBallGameObject ball4 = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp145_142 = this.currentSnowWar; int tmp149_146 = tmp145_142.objectIdCounter;tmp145_142.objectIdCounter = (tmp149_146 + 1);ball4.objectId = tmp149_146;
    SnowBallGameObject ball5 = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp177_174 = this.currentSnowWar; int tmp181_178 = tmp177_174.objectIdCounter;tmp177_174.objectIdCounter = (tmp181_178 + 1);ball5.objectId = tmp181_178;
    SnowBallGameObject ball6 = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp209_206 = this.currentSnowWar; int tmp213_210 = tmp209_206.objectIdCounter;tmp209_206.objectIdCounter = (tmp213_210 + 1);ball6.objectId = tmp213_210;
    synchronized (this.currentSnowWar.gameEvents)
    {
      this.currentSnowWar.gameEvents.add(new BallThrowToPosition(this.humanObject, destX * Tile.TILE_SIZE, destY * Tile.TILE_SIZE, 3));
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball, this.humanObject, destX * Tile.TILE_SIZE, destY * Tile.TILE_SIZE, 3));
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball4, this.humanObject, destX * Tile.TILE_SIZE, (destY + 1) * Tile.TILE_SIZE, 3));
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball1, this.humanObject, (destX + 1) * Tile.TILE_SIZE, destY * Tile.TILE_SIZE, 3));
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball6, this.humanObject, (destX - 1) * Tile.TILE_SIZE, (destY + 1) * Tile.TILE_SIZE, 3));
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball2, this.humanObject, (destX - 1) * Tile.TILE_SIZE, (destY - 1) * Tile.TILE_SIZE, 3));
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball3, this.humanObject, (destX + 1) * Tile.TILE_SIZE, (destY - 1) * Tile.TILE_SIZE, 3));
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball5, this.humanObject, (destX + 1) * Tile.TILE_SIZE, (destY + 1) * Tile.TILE_SIZE, 3));
    }
  }
  
  public void throwSnowballAtHuman(int victim, int type)
  {
    if (!this.humanObject.canThrowSnowBall()) {
      return;
    }
    GameItemObject vict = (GameItemObject)this.currentSnowWar.gameObjects.get(Integer.valueOf(victim));
    if (vict == null) {
      return;
    }
    SnowBallGameObject ball = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp55_52 = this.currentSnowWar; int tmp59_56 = tmp55_52.objectIdCounter;tmp55_52.objectIdCounter = (tmp59_56 + 1);ball.objectId = tmp59_56;
    synchronized (this.currentSnowWar.gameEvents)
    {
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball, this.humanObject, vict.location3D().x(), vict.location3D().y(), type));
      this.currentSnowWar.gameEvents.add(new BallThrowToHuman(this.humanObject, (HumanGameObject)vict, 0));
    }
  }
  
  public void throwSnowballAtPosition(int destX, int destY, int type)
  {
    if (!this.humanObject.canThrowSnowBall()) {
      return;
    }
    SnowBallGameObject ball = new SnowBallGameObject(this.currentSnowWar); SnowWarRoom 
      tmp30_27 = this.currentSnowWar; int tmp34_31 = tmp30_27.objectIdCounter;tmp30_27.objectIdCounter = (tmp34_31 + 1);ball.objectId = tmp34_31;
    synchronized (this.currentSnowWar.gameEvents)
    {
      this.currentSnowWar.gameEvents.add(new CreateSnowBall(ball, this.humanObject, destX, destY, type));
      this.currentSnowWar.gameEvents.add(new BallThrowToPosition(this.humanObject, destX, destY, 0));
    }
  }
}


