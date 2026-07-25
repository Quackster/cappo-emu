package cappo.game.pets;

public class Terrier
  extends PetBase
{
  public Terrier(short raceId)
  {
    super(raceId);
    
    addSpeech((short)3, "woof woof woof");
    addSpeech((short)3, "Auuuu auuuu");
    addSpeech((short)3, "wooooof");
    addSpeech((short)3, "Grrrr");
    addSpeech((short)3, "Sentandose");
    addSpeech((short)3, "*Estornunando*");
    addSpeech((short)3, "lay");
    addSpeech((short)3, "Woof");
  }
  
  public Terrier()
  {
    addRace(new Terrier((short)0));
    addRace(new Terrier((short)1));
    addRace(new Terrier((short)2));
    addRace(new Terrier((short)3));
    addRace(new Terrier((short)4));
    addRace(new Terrier((short)5));
    addRace(new Terrier((short)6));
    addRace(new Terrier((short)7));
    addRace(new Terrier((short)8));
    addRace(new Terrier((short)9));
    addRace(new Terrier((short)10));
    addRace(new Terrier((short)11));
    addRace(new Terrier((short)12));
  }
}


