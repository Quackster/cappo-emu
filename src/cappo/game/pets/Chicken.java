package cappo.game.pets;

public class Chicken
  extends PetBase
{
  public Chicken(short raceId)
  {
    super(raceId);
  }
  
  public Chicken()
  {
    addRace(new Chicken((short)0));
    addRace(new Chicken((short)1));
    addRace(new Chicken((short)2));
    addRace(new Chicken((short)3));
    addRace(new Chicken((short)4));
  }
}


