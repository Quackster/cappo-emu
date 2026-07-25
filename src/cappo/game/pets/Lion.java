package cappo.game.pets;

public class Lion
  extends PetBase
{
  public Lion(short raceId)
  {
    super(raceId);
    
    addSpeech((short)3, "Agr...");
    addSpeech((short)3, "Grrrrr.... grrrr....");
    addSpeech((short)3, "Grrrrr...rawh!");
    addSpeech((short)3, "snf");
    addSpeech((short)3, "Grrrrrrh...");
    addSpeech((short)3, "snf");
    addSpeech((short)3, "lay");
    addSpeech((short)3, "Grr...");
    addSpeech((short)3, "*rugiendo*");
  }
  
  public Lion()
  {
    addRace(new Lion((short)0));
    addRace(new Lion((short)1));
    addRace(new Lion((short)2));
    addRace(new Lion((short)3));
    addRace(new Lion((short)4));
    addRace(new Lion((short)5));
    addRace(new Lion((short)6));
    addRace(new Lion((short)7));
    addRace(new Lion((short)8));
    addRace(new Lion((short)9));
    addRace(new Lion((short)10));
    addRace(new Lion((short)11));
    addRace(new Lion((short)12));
    addRace(new Lion((short)13));
  }
}


