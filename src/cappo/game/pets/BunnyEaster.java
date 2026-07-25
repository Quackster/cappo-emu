package cappo.game.pets;

public class BunnyEaster
  extends PetBase
{
  public BunnyEaster(short raceId)
  {
    super(raceId);
  }
  
  public BunnyEaster()
  {
    addRace(new BunnyEaster((short)0));
    addRace(new BunnyEaster((short)1));
    addRace(new BunnyEaster((short)2));
    addRace(new BunnyEaster((short)3));
    addRace(new BunnyEaster((short)4));
  }
}


