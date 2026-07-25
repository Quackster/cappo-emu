package cappo.game.pets;

public class Cat
  extends PetBase
{
  public Cat(short raceId)
  {
    super(raceId);
    
    addSpeech((short)3, "miauu");
    addSpeech((short)3, "Hmmmm");
    addSpeech((short)3, "*Estornudando");
    addSpeech((short)3, "Lamer pata");
    addSpeech((short)3, "Sentandose");
    addSpeech((short)3, "Oliendo");
  }
  
  public Cat()
  {
    addRace(new Cat((short)0));
    addRace(new Cat((short)1));
    addRace(new Cat((short)2));
    addRace(new Cat((short)3));
    addRace(new Cat((short)4));
    addRace(new Cat((short)5));
    addRace(new Cat((short)6));
    addRace(new Cat((short)7));
    addRace(new Cat((short)8));
    addRace(new Cat((short)9));
    addRace(new Cat((short)10));
    addRace(new Cat((short)11));
    addRace(new Cat((short)12));
    addRace(new Cat((short)13));
    addRace(new Cat((short)14));
    addRace(new Cat((short)15));
    addRace(new Cat((short)16));
    addRace(new Cat((short)17));
    addRace(new Cat((short)18));
    addRace(new Cat((short)19));
    addRace(new Cat((short)20));
    addRace(new Cat((short)21));
    addRace(new Cat((short)22));
    addRace(new Cat((short)23));
    addRace(new Cat((short)24));
    addRace(new Cat((short)25));
  }
}


