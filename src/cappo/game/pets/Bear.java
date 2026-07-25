package cappo.game.pets;

public class Bear
  extends PetBase
{
  public Bear(short raceId)
  {
    super(raceId);
    
    addSpeech((short)3, "*Dame un salmón fresco por favor*");
    addSpeech((short)3, "Grrrrrrr");
    addSpeech((short)3, "*Estornudando*");
    addSpeech((short)3, "Grrrr... grrrr");
  }
  
  public Bear()
  {
    addRace(new Bear((short)0));
    addRace(new Bear((short)1));
    addRace(new Bear((short)2));
    addRace(new Bear((short)3));
    addRace(new Bear((short)4));
  }
}


