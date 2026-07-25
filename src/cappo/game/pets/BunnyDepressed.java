package cappo.game.pets;

public class BunnyDepressed
  extends PetBase
{
  public BunnyDepressed(short raceId)
  {
    super(raceId);
  }
  
  public BunnyDepressed()
  {
    addRace(new BunnyDepressed((short)0));
  }
}


