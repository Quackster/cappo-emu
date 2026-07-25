package cappo.game.pets;

public class Turtle
  extends PetBase
{
  public Turtle(short raceId)
  {
    super(raceId);
  }
  
  public Turtle()
  {
    addRace(new Turtle((short)0));
    addRace(new Turtle((short)1));
    addRace(new Turtle((short)2));
    addRace(new Turtle((short)3));
    addRace(new Turtle((short)4));
    addRace(new Turtle((short)5));
    addRace(new Turtle((short)6));
    addRace(new Turtle((short)7));
    addRace(new Turtle((short)8));
    addRace(new Turtle((short)9));
    addRace(new Turtle((short)10));
    addRace(new Turtle((short)11));
    addRace(new Turtle((short)12));
  }
}


