package cappo.game.bots;

import cappo.engine.logging.Log;
import cappo.game.collections.Utils;
import cappo.game.player.AvatarLook;
import cappo.game.roomengine.entity.live.RentalBotEntity;
import java.util.ArrayList;
import java.util.List;

public class RentalBot
{
  public static final int PROPERTY_UNDEFINED1 = 0;
  public static final int PROPERTY_DRESS = 1;
  public static final int PROPERTY_CHAT = 2;
  public static final int PROPERTY_WALK = 3;
  public static final int PROPERTY_DANCE = 4;
  public static final int PROPERTY_NAME = 5;
  public static final int PROPERTY_UNDEFINED2 = 6;
  public static final short BOT_GENERIC = 0;
  public static final short BOT_BARTENDER = 1;
  public static final short BOT_LIMIT = 2;
  public static final BotBase[] BOTS = {
  
    new BotGeneric(), 
    
    new BotBarTender() };
  public int id;
  public String name;
  public String motto;
  public String gender;
  public AvatarLook botLook;
  public int ownerId;
  public String ownerName;
  public BotBase base;
  public short botType;
  public RentalBotEntity botEntity;
  public boolean needInsert;
  public List<String> speeches = new ArrayList();
  public boolean danceEnabled;
  public boolean walkRandomEnabled = true;
  public boolean chatAuto = true;
  public int chatDelay = 7;
  public long nextChat = 0L;
  
  public RentalBot(int iId, String sName, short type)
  {
    this.id = iId;
    this.name = sName;
    this.botType = type;
    this.base = BOTS[type];
    if (this.base == null) {
      Log.printLog("Unknown bot type: " + type);
    }
  }
  
  public void setDefaults()
  {
    this.needInsert = true;
    
    this.motto = "I am a bot!";
    this.botLook = this.base.defaultLook;
    this.gender = this.base.defaultGender;
  }
  
  public String getSpeech()
  {
    if (this.speeches.isEmpty()) {
      return null;
    }
    return (String)this.speeches.get(Utils.GetRandomNumber(0, this.speeches.size() - 1));
  }
}


