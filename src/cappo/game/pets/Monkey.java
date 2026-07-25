package cappo.game.pets;

public class Monkey
  extends PetBase
{
  public Monkey(short raceId)
  {
    super(raceId);
  }
  
  public Monkey()
  {
    addRace(new Monkey((short)0));
    addRace(new Monkey((short)1));
    addRace(new Monkey((short)2));
    addRace(new Monkey((short)3));
    addRace(new Monkey((short)4));
    addRace(new Monkey((short)5));
    addRace(new Monkey((short)6));
    addRace(new Monkey((short)7));
    addRace(new Monkey((short)8));
  }
}


