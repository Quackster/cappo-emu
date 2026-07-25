package cappo.game.games.snowwar.gameevents;

import cappo.game.games.snowwar.PlayerTile;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.games.snowwar.gameobjects.SnowBallGameObject;

public class CreateSnowBall
  extends Event
{
  public SnowBallGameObject ball;
  public HumanGameObject player;
  public int x;
  public int y;
  public int type;
  
  public CreateSnowBall(SnowBallGameObject ball, HumanGameObject player, int x, int y, int type)
  {
    this.EventType = 8;
    this.ball = ball;
    this.player = player;
    this.x = x;
    this.y = y;
    this.type = type;
  }
  
  public void apply()
  {
    this.ball.initialize(this.player.location3D().x(), this.player.location3D().y(), this.type, this.x, this.y, this.player);
    this.ball.GenerateCHECKSUM(this.player.currentSnowWar, 1);
    this.player.currentSnowWar.addGameObject(this.ball);
  }
}


