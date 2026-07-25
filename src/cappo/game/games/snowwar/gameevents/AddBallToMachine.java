package cappo.game.games.snowwar.gameevents;

import cappo.game.games.snowwar.gameobjects.MachineGameObject;

public class AddBallToMachine
  extends Event
{
  public MachineGameObject gameItem;
  
  public AddBallToMachine(MachineGameObject gameItem)
  {
    this.EventType = 11;
    this.gameItem = gameItem;
  }
  
  public void apply()
  {
    this.gameItem.addSnowBall();
  }
}


