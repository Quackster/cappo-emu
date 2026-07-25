package cappo.game.games.snowwar.gameevents;

import cappo.game.games.snowwar.gameobjects.HumanGameObject;

public class BallThrowToPosition
  extends Event
{
  public HumanGameObject attacker;
  public int x;
  public int y;
  public int type;
  
  public BallThrowToPosition(HumanGameObject attacker, int x, int y, int type)
  {
    this.EventType = 4;
    this.attacker = attacker;
    this.x = x;
    this.y = y;
    this.type = type;
  }
  
  public void apply()
  {
    this.attacker._vs(this.x, this.y);
    this.attacker._w1();
  }
}


