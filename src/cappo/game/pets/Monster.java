package cappo.game.pets;

public class Monster
  extends PetBase
{
  public Monster(short raceId)
  {
    super(raceId);
  }
  
  public Monster()
  {
    addRace(new Monster((short)0));
  }
}


