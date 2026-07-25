package cappo.game.pets;

public class Dog
  extends PetBase
{
  public Dog(short raceId)
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
  
  public Dog()
  {
    addRace(new Dog((short)0));
    addRace(new Dog((short)1));
    addRace(new Dog((short)2));
    addRace(new Dog((short)3));
    addRace(new Dog((short)4));
    addRace(new Dog((short)5));
    addRace(new Dog((short)6));
    addRace(new Dog((short)7));
    addRace(new Dog((short)8));
    addRace(new Dog((short)9));
    addRace(new Dog((short)10));
    addRace(new Dog((short)11));
    addRace(new Dog((short)12));
    addRace(new Dog((short)13));
    addRace(new Dog((short)14));
    addRace(new Dog((short)15));
    addRace(new Dog((short)16));
    addRace(new Dog((short)17));
    addRace(new Dog((short)18));
    addRace(new Dog((short)19));
    addRace(new Dog((short)20));
    addRace(new Dog((short)21));
    addRace(new Dog((short)22));
    addRace(new Dog((short)23));
    addRace(new Dog((short)24));
    addRace(new Dog((short)25));
  }
}


