package cappo.game.pets;

public class PigeOnGood
  extends PetBase
{
  public PigeOnGood(short raceId)
  {
    super(raceId);
  }
  
  public PigeOnGood()
  {
    addRace(new PigeOnGood((short)0));
  }
}


