package cappo.game.pets;

public class Dragon
  extends PetBase
{
  public Dragon(short raceId)
  {
    super(raceId);
  }
  
  public Dragon()
  {
    addRace(new Dragon((short)0));
    addRace(new Dragon((short)1));
    addRace(new Dragon((short)2));
    addRace(new Dragon((short)3));
    addRace(new Dragon((short)4));
    addRace(new Dragon((short)5));
    addRace(new Dragon((short)6));
    addRace(new Dragon((short)7));
    addRace(new Dragon((short)8));
    addRace(new Dragon((short)9));
    addRace(new Dragon((short)10));
  }
}


