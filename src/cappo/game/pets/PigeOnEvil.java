package cappo.game.pets;

public class PigeOnEvil
  extends PetBase
{
  public PigeOnEvil(short raceId)
  {
    super(raceId);
  }
  
  public PigeOnEvil()
  {
    addRace(new PigeOnEvil((short)0));
  }
}


