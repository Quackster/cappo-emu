package cappo.game.pets;

public class BunnyLove
  extends PetBase
{
  public BunnyLove(short raceId)
  {
    super(raceId);
  }
  
  public BunnyLove()
  {
    addRace(new BunnyLove((short)0));
  }
}


