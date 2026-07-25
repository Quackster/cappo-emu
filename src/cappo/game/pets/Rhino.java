package cappo.game.pets;

public class Rhino
  extends PetBase
{
  public Rhino(short raceId)
  {
    super(raceId);
    
    addSpeech((short)3, "Auguruuuh...");
    addSpeech((short)3, "Buff");
    addSpeech((short)3, "Augubuff...");
    addSpeech((short)3, "Buffuu...");
    addSpeech((short)3, "snf");
    addSpeech((short)3, "lay");
    addSpeech((short)3, "Aff");
  }
  
  public Rhino()
  {
    addRace(new Rhino((short)0));
    addRace(new Rhino((short)1));
    addRace(new Rhino((short)2));
    addRace(new Rhino((short)3));
    addRace(new Rhino((short)4));
    addRace(new Rhino((short)5));
    addRace(new Rhino((short)6));
    addRace(new Rhino((short)7));
    addRace(new Rhino((short)8));
  }
}


