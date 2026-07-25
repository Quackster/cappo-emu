package cappo.game.pets;

import cappo.engine.logging.Log;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.live.PetEntity;
import java.util.Map;

public class Pet
{
  public static final short PET_DOG = 0;
  public static final short PET_CAT = 1;
  public static final short PET_CROCO = 2;
  public static final short PET_TERRIER = 3;
  public static final short PET_BEAR = 4;
  public static final short PET_PIG = 5;
  public static final short PET_LION = 6;
  public static final short PET_RHINO = 7;
  public static final short PET_SPIDER = 8;
  public static final short PET_TURTLE = 9;
  public static final short PET_CHICKEN = 10;
  public static final short PET_FROG = 11;
  public static final short PET_DRAGON = 12;
  public static final short PET_MONSTER = 13;
  public static final short PET_MONKEY = 14;
  public static final short PET_HORSE = 15;
  public static final short PET_MONSTERPLANT = 16;
  public static final short PET_BUNNYEASTER = 17;
  public static final short PET_BUNNYEVIL = 18;
  public static final short PET_BUNNYDEPRESSED = 19;
  public static final short PET_BUNNYLOVE = 20;
  public static final short PET_PIGEONGOOD = 21;
  public static final short PET_PIGEONEVIL = 22;
  public static final short PET_DEMONMONKEY = 23;
  public static final short PET_BEARBABY = 24;
  public static final short PET_TERRIERBABY = 25;
  public static final short PET_GNOME = 26;
  public static final short PET_LIMIT = 27;
  public static final PetBase[] PETS = {
  
    new Dog(), 
    
    new Cat(), 
    
    new Croco(), 
    
    new Terrier(), 
    
    new Bear(), 
    
    new Pig(), 
    
    new Lion(), 
    
    new Rhino(), 
    
    new Spider(), 
    
    new Turtle(), 
    
    new Chicken(), 
    
    new Frog(), 
    
    new Dragon(), 
    
    new Monster(), 
    
    new Monkey(), 
    
    new Horse(), 
    
    new Monsterplant(), 
    
    new BunnyEaster(), 
    
    new BunnyEvil(), 
    
    new BunnyDepressed(), 
    
    new BunnyLove(), 
    
    new PigeOnGood(), 
    
    new PigeOnEvil(), 
    
    new PigeOnEvil(), 
    
    new PigeOnEvil(), 
    
    new PigeOnEvil(), 
    
    new PigeOnEvil() };
  public String Color;
  public int Energy;
  public int happiness;
  public int Experience;
  public int id;
  public int level;
  public String name;
  public int Nutrition;
  public int ownerId;
  public String ownerName;
  public int Respects;
  public long TimeCreated;
  public PetBase base;
  public short petType;
  public boolean haveSaddle;
  public FloorItem saddleFurni;
  public boolean ridingAll;
  public PetEntity petEntity;
  public boolean needInsert;
  
  public Pet(int iId, String sName, short type, short race, String color)
  {
    this.id = iId;
    this.name = sName;
    this.Color = color;
    this.petType = type;
    this.base = ((PetBase)PETS[type].races.get(Short.valueOf(race)));
    if (this.base == null) {
      Log.printLog("Unknown pet race, type=" + type + " race=" + race);
    }
  }
}


