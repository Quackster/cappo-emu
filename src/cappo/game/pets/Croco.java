package cappo.game.pets;

public class Croco
  extends PetBase
{
  public Croco(short raceId)
  {
    super(raceId);
    
    addSpeech((short)3, "Rrrr....Grrrrrg....");
    addSpeech((short)3, "*Abrir boca*");
    addSpeech((short)3, "Tick tock tick....");
    addSpeech((short)3, "Mover cola");
    addSpeech((short)3, "Estornudando");
  }
  
  public Croco()
  {
    addRace(new Croco((short)0));
    addRace(new Croco((short)1));
    addRace(new Croco((short)2));
    addRace(new Croco((short)3));
    addRace(new Croco((short)4));
    addRace(new Croco((short)5));
    addRace(new Croco((short)6));
    addRace(new Croco((short)7));
    addRace(new Croco((short)8));
    addRace(new Croco((short)9));
    addRace(new Croco((short)10));
    addRace(new Croco((short)11));
    addRace(new Croco((short)12));
  }
}


