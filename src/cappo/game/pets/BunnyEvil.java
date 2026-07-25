package cappo.game.pets;

public class BunnyEvil
  extends PetBase
{
  public BunnyEvil(short raceId)
  {
    super(raceId);
  }
  
  public BunnyEvil()
  {
    addRace(new BunnyEvil((short)0));
  }
}


