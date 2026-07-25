package cappo.game.games.snowwar.gameevents;

import cappo.game.games.snowwar.gameobjects.HumanGameObject;

public class MakeSnowBall
  extends Event
{
  public HumanGameObject player;
  
  public MakeSnowBall(HumanGameObject player)
  {
    this.EventType = 7;
    this.player = player;
  }
  
  public void apply()
  {
    this.player.makeSnowBall();
  }
}


