package cappo.game.pets;

import cappo.game.collections.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PetBase
{
  public static final short SPEECH_NOT_FOUND = 0;
  public static final short SPEECH_NO_ENERGY = 1;
  public static final short SPEECH_REFUSE = 2;
  public static final short SPEECH_RANDOM = 3;
  public short raceId;
  public Map<Short, List<String>> speeches = new ConcurrentHashMap();
  public Map<Short, PetBase> races = new ConcurrentHashMap();
  public static final int[] ExperienceLevels = { 100, 200, 300, 400, 600, 900, 1300, 1800, 2400, 3200, 4300, 5700, 7600, 10100, 13300, 17500, 23000, 31000, 39600, 51900, 51900 };
  public static final int[] MaxEnergyLevels = { 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 320, 340, 360, 380, 400, 420, 440, 460, 480, 500, 500 };
  public static final int[] MaxHappinessLevels = { 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 320, 340, 360, 380, 400, 420, 440, 460, 480, 500, 500 };
  public static final int MaxLevel = 20;
  public static final int MaxHappiness = 100;
  
  public PetBase() {}
  
  public PetBase(short race)
  {
    this.raceId = race;
    
    this.speeches.put(Short.valueOf((short)0), new ArrayList());
    this.speeches.put(Short.valueOf((short)1), new ArrayList());
    this.speeches.put(Short.valueOf((short)2), new ArrayList());
    this.speeches.put(Short.valueOf((short)3), new ArrayList());
    
    List<String> notfound = (List)this.speeches.get(Short.valueOf((short)0));
    List<String> noenergy = (List)this.speeches.get(Short.valueOf((short)1));
    List<String> refuse = (List)this.speeches.get(Short.valueOf((short)2));
    
    notfound.add("*Confundido*");
    notfound.add("¿Qué quieres?");
    notfound.add("No te entiendo");
    notfound.add("¿Qué es eso?");
    
    noenergy.add("ZzZzzzzz");
    noenergy.add("*Estoy cansado*");
    noenergy.add("Cansado *Está cansado*");
    noenergy.add("ZzZzZZzzzZZz");
    noenergy.add("zzZzzZzzz");
    noenergy.add("... Con sueño ..");
    noenergy.add("ZzZzzZ");
    
    refuse.add("*Me niego*");
    refuse.add(" ... ");
    refuse.add("¿Quién te crees que eres?");
    refuse.add("¿Qué haces?");
    refuse.add("Grrrrr");
    refuse.add("*Tengo ganas de jugar*");
    refuse.add("¿Por qué?");
  }
  
  public boolean checkLevel(Pet pet)
  {
    int newlevel = pet.level;
    while ((newlevel < 20) && 
      (pet.Experience > ExperienceLevels[newlevel])) {
      newlevel++;
    }
    if (newlevel > pet.level)
    {
      pet.level = newlevel;
      return true;
    }
    return false;
  }
  
  public void addRace(PetBase petBase)
  {
    this.races.put(Short.valueOf(petBase.raceId), petBase);
  }
  
  public void addSpeech(short key, String value)
  {
    List<String> speech = (List)this.speeches.get(Short.valueOf(key));
    speech.add(value);
  }
  
  public String getSpeech(short key)
  {
    List<String> speech = (List)this.speeches.get(Short.valueOf(key));
    if (speech.isEmpty()) {
      return "";
    }
    return (String)speech.get(Utils.GetRandomNumber(0, speech.size() - 1));
  }
}


