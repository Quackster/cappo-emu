package cappo.game.pets;

public class Pig
  extends PetBase
{
  public Pig(short raceId)
  {
    super(raceId);
    
    addSpeech((short)3, "Oink Oink..");
    addSpeech((short)3, "*Meando*");
    addSpeech((short)3, "*Estornudando*");
    addSpeech((short)3, "*Tirandose un pedo*");
    addSpeech((short)3, "Oink!");
    addSpeech((short)3, "*Estornunando*");
    addSpeech((short)3, "*Hacer el cerdo*");
    addSpeech((short)3, "oink");
  }
  
  public Pig()
  {
    addRace(new Pig((short)0));
    addRace(new Pig((short)1));
    addRace(new Pig((short)2));
    addRace(new Pig((short)3));
    addRace(new Pig((short)4));
    addRace(new Pig((short)5));
    addRace(new Pig((short)6));
    addRace(new Pig((short)7));
    addRace(new Pig((short)8));
    addRace(new Pig((short)9));
    addRace(new Pig((short)10));
    addRace(new Pig((short)11));
    addRace(new Pig((short)12));
  }
}


