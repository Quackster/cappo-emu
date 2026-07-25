package cappo.game.collections;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.game.catalog.giftwrapping.GiftWrappingConfiguration;
import cappo.game.roomengine.entity.item.extradata.StuffDataWriter;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseItem
{
  public static final int CATEGORY_DEFAULT = 1;
  public static final int CATEGORY_WALLPAPER = 2;
  public static final int CATEGORY_FLOORSINGLE = 3;
  public static final int CATEGORY_LANDSCAPE = 4;
  public static final int CATEGORY_POSTIT = 5;
  public static final int CATEGORY_POSTER = 6;
  public static final int CATEGORY_SONGDISK_OLD = 7;
  public static final int CATEGORY_SONGDISK = 8;
  public static final int CATEGORY_PRESENT = 9;
  public static final int CATEGORY_XMAS = 10;
  public static final int CATEGORY_TROPHY = 11;
  public static final int CATEGORY_HORSE_SHAMPOO = 13;
  public static final int CATEGORY_HORSE_HAIR_STYLE = 14;
  public static final int CATEGORY_HORSE_HAIR_SHAMPOO = 15;
  public static final int CATEGORY_SADDLE = 16;
  public static final int CATEGORY_GROUPFURNIS = 17;
  public static final int CATEGORY_SNOWWAR = 18;
  public static final int CATEGORY_MONSTERPLANT_SEED = 19;
  public static final int CATEGORY_MONSTERPLANT_REVIVAL = 20;
  public static final int CATEGORY_MONSTERPLANT_REBREED = 21;
  public static final int CATEGORY_MONSTERPLANT_FERTILIZE = 22;
  public static final int WIRED_EFFECT_SHOWMESSAGE = 7;
  public static final int WIRED_EFFECT_TELEPORT = 13;
  public static final int WIRED_EFFECT_MOVEANDROTATE = 16;
  
  public static enum ItemType
  {
    WIRED_TRIGGER,  WIRED_EFFECT,  WIRED_CONDITION,  ROOMGAME_GATE,  ROOMGAME_SCORE,  FOOTBALL_GOAL,  WATER;
  }
  
  public static enum FurniLogic
  {
    BASIC,  MULTISTATE,  CRACKABLE,  MANNEQUIN,  ROOMDIMMER,  BADGEDISPLAY,  MULTIHEIGHT,  FLOORHOLE;
  }
  
  private static FurniLogic getLogic(String furniLogic)
  {
    if (furniLogic.equals("furniture_multistate")) {
      return FurniLogic.MULTISTATE;
    }
    if (furniLogic.equals("furniture_crackable")) {
      return FurniLogic.CRACKABLE;
    }
    if (furniLogic.equals("furniture_mannequin")) {
      return FurniLogic.MANNEQUIN;
    }
    if (furniLogic.equals("furniture_roomdimmer")) {
      return FurniLogic.ROOMDIMMER;
    }
    if (furniLogic.equals("furniture_badge_display")) {
      return FurniLogic.BADGEDISPLAY;
    }
    if (furniLogic.equals("furniture_multiheight")) {
      return FurniLogic.MULTIHEIGHT;
    }
    if (furniLogic.equals("furniture_floor_hole")) {
      return FurniLogic.FLOORHOLE;
    }
    return FurniLogic.BASIC;
  }
  
  public static final BaseItem snst_tree1_d = new BaseItem("s", 4061, "snst_tree1_d", 1, 1);
  public static final BaseItem snst_block1 = new BaseItem("s", 4066, "snst_block1", 1, 1);
  public static final BaseItem snst_ballpile = new BaseItem("s", 4059, "snst_ballpile", 1, 1);
  public static final BaseItem xm09_man_a = new BaseItem("s", 3038, "xm09_man_a", 1, 1);
  public static final BaseItem xm09_man_c = new BaseItem("s", 3032, "xm09_man_c", 1, 1);
  public static final BaseItem xm09_man_b = new BaseItem("s", 3037, "xm09_man_b", 1, 1);
  public static final BaseItem snst_fence = new BaseItem("s", 4062, "snst_fence", 1, 2);
  public static final BaseItem ads_background = new BaseItem("s", 3704, "ads_background", 1, 1);
  public static final BaseItem snst_tree1 = new BaseItem("s", 4063, "snst_tree1", 1, 1);
  public static final BaseItem s_snowball_machine = new BaseItem("s", 4068, "s_snowball_machine", 1, 1);
  public static final BaseItem snst_iceblock = new BaseItem("s", 4064, "snst_iceblock", 1, 1);
  public static final BaseItem ads_igorraygun = new BaseItem("s", 2648, "ads_igorraygun", 1, 2);
  
  public BaseItem(String type, int id, String name, int xdim, int ydim)
  {
    this.Type = type;
    this.SpriteId = id;
    this.Name = name;
    this.xDim = xdim;
    this.yDim = ydim;
    this.Height = 1.0F;
  }
  
  public static Map<Integer, BaseItem> baseItems = new HashMap(5000);
  public int Id;
  public int SpriteId;
  public String Name;
  public String Type;
  public int xDim;
  public int yDim;
  public float Height;
  public boolean AllowStack;
  public boolean allowWalk;
  public boolean allowLay;
  public boolean allowSit;
  public boolean AllowRecycle;
  public boolean AllowTrade;
  public boolean AllowMarketplaceSell;
  public boolean AllowGift;
  public boolean AllowInventoryStack;
  public List<Integer> vendingIds;
  public int interactionCount;
  public Interactor interactor;
  public Interactor.InteractorType interactorType;
  public ItemType itemType;
  public FurniLogic logic;
  public int itemExtraType = 0;
  public int itemCategory = 1;
  
  public BaseItem() {}
  
  public static StuffDataWriter upgradeStuffData(BaseItem base, String extra)
  {
    StuffDataWriter data = null;
    if (base.itemExtraType == 0)
    {
      data = new StuffDataWriter(0);
      data.writeString(extra == null ? "" : extra);
    }
    else if (base.itemExtraType == 2)
    {
      data = new StuffDataWriter(2);
      data.writeInt8(((Integer)data.setSaved(Integer.valueOf(0))).intValue());
      
      int size = 0;
      if (extra != null)
      {
        String[] parts = extra.split(";");
        for (String val : parts)
        {
          data.writeString(val);
          size++;
        }
      }
      data.writeSavedInt8(size);
    }
    else if (base.itemExtraType == 1)
    {
      data = new StuffDataWriter(1);
      data.writeInt8(((Integer)data.setSaved(Integer.valueOf(0))).intValue());
      
      int size = 0;
      if (extra != null)
      {
        String[] values = extra.split("\t");
        for (String part : values) {
          if ((!part.isEmpty()) && (!part.equals("=")))
          {
            String[] a = part.split("=");
            if (a.length == 2)
            {
              data.writeString(a[0]);
              data.writeString(a[1]);
              size++;
            }
          }
        }
      }
      data.writeSavedInt8(size);
    }
    else if (base.itemExtraType == 5)
    {
      data = new StuffDataWriter(5);
      data.writeInt8(((Integer)data.setSaved(Integer.valueOf(0))).intValue());
      
      int size = 0;
      if (extra != null)
      {
        String[] parts = extra.split(";");
        for (String val : parts)
        {
          data.writeInt32(Integer.parseInt(val));
          size++;
        }
      }
      data.writeSavedInt8(size);
    }
    else if (base.itemExtraType == 3)
    {
      data = new StuffDataWriter(3);
      data.writeString(extra == null ? "" : extra);
      data.writeInt16(0);
    }
    return data;
  }
  
  public static void Init(DBResult result)
    throws Exception
  {
    GiftWrappingConfiguration.baseGiftItems.clear();
    baseItems.clear();
    
    Database.query(result, "SELECT * FROM furnis_base;", new Object[0]);
    while (result.data.next()) {
      GenerateFurniture(result.data);
    }
  }
  
  private static void GenerateFurniture(ResultSet userdata)
    throws Exception
  {
    BaseItem item = new BaseItem();
    
    item.Id = userdata.getInt("baseid");
    baseItems.put(Integer.valueOf(item.Id), item);
    
    item.SpriteId = userdata.getInt("id");
    item.Name = userdata.getString("classname");
    
    int furniType = userdata.getInt("type");
    item.Type = (furniType == 5 ? "h" : furniType == 4 ? "r" : furniType == 3 ? "e" : furniType == 2 ? "i" : furniType == 1 ? "s" : "");
    
    item.xDim = userdata.getInt("xdim");
    item.yDim = userdata.getInt("ydim");
    item.Height = userdata.getFloat("height");
    item.allowWalk = (userdata.getInt("canstandon") == 1);
    item.allowLay = (userdata.getInt("canlayon") == 1);
    item.allowSit = ((userdata.getInt("cansiton") == 1) || (item.allowLay));
    item.AllowStack = (userdata.getInt("allow_stack") == 1);
    
    item.itemCategory = userdata.getInt("specialtype");
    
    item.AllowRecycle = (userdata.getInt("allow_recycle") == 1);
    item.AllowTrade = (userdata.getInt("allow_trade") == 1);
    item.AllowMarketplaceSell = (userdata.getInt("allow_marketplace_sell") == 1);
    item.AllowGift = (userdata.getInt("allow_gift") == 1);
    item.AllowInventoryStack = (userdata.getInt("allow_inventory_stack") == 1);
    String tmp = userdata.getString("vending_ids");
    if (!tmp.isEmpty())
    {
      String[] values = tmp.split(",");
      item.vendingIds = new ArrayList(values.length);
      for (String val : values) {
        item.vendingIds.add(Integer.valueOf(Integer.parseInt(val.trim())));
      }
    }
    item.logic = getLogic(userdata.getString("furni_logic"));
    
    item.itemExtraType = userdata.getInt("extradata_type");
    item.interactionCount = userdata.getInt("cycle_count");
    String interactionKey = userdata.getString("interaction_type");
    switch (interactionKey)
    {
      case "triggerwalkonfurni":
      case "triggerwalkofffurni":
      case "triggergameend":
      case "triggergamestart":
      case "triggertimer":
      case "triggerrepeater":
      case "triggerroomenter":
      case "triggeronusersay":
      case "triggerstatechanged":
      case "triggerscoreachieved":
        item.itemType = ItemType.WIRED_TRIGGER;
        item.interactor = Interactor.iterWired;
        item.interactorType = Interactor.GetInteractorType(interactionKey);
        break;
      case "conditionfurnishaveusers":
      case "conditionstatepos":
      case "conditiontriggeronfurni":
      case "conditiontimelessthan":
      case "conditiontimemorethan":
        item.itemType = ItemType.WIRED_CONDITION;
        item.interactor = Interactor.iterWired;
        item.interactorType = Interactor.GetInteractorType(interactionKey);
        break;
      case "actionmoverotate":
      case "actiongivescore":
      case "actionposreset":
      case "actionresettimer":
      case "actiongivereward":
      case "actionteleportto":
      case "actiontogglestate":
      case "actionshowmessage":
        item.itemType = ItemType.WIRED_EFFECT;
        item.interactor = Interactor.iterWired;
        item.interactorType = Interactor.GetInteractorType(interactionKey);
        break;
      case "fbgate":
      case "banzaigatered":
      case "banzaigategreen":
      case "banzaigateblue":
      case "banzaigateyellow":
      case "freezebluegate":
      case "freezegreengate":
      case "freezeredgate":
      case "freezeyellowgate":
        item.itemType = ItemType.ROOMGAME_GATE;
        break;
      case "banzaiscorered":
      case "banzaiscoregreen":
      case "banzaiscoreblue":
      case "banzaiscoreyellow":
      case "footballcountered":
      case "footballcounterblue":
      case "footballcountergreen":
      case "footballcounteryellow":
        item.itemType = ItemType.ROOMGAME_SCORE;
        break;
      case "footballgoalred":
      case "footballgoalblue":
      case "footballgoalgreen":
      case "footballgoalyellow":
        item.itemType = ItemType.FOOTBALL_GOAL;
        break;
      case "haloweenpool":
      case "pool":
      case "lowpool":
        item.itemType = ItemType.WATER;
        break;
      default:
        break;
    }
    if (item.interactorType == null)
    {
      item.interactorType = Interactor.GetInteractorType(interactionKey);
      if (item.interactorType == Interactor.InteractorType.gift) {
        GiftWrappingConfiguration.addGift(item);
      }
      if (item.logic == FurniLogic.MANNEQUIN) {
        item.interactor = Interactor.iterOutfit;
      } else if (interactionKey.equals("teleport")) {
        item.interactor = Interactor.iterTeleport;
      } else if (interactionKey.equals("vendingmachine")) {
        item.interactor = Interactor.iterVendingMachine;
      } else if (interactionKey.equals("onewaygate")) {
        item.interactor = Interactor.iterOneWayGate;
      } else if (interactionKey.equals("dice")) {
        item.interactor = Interactor.iterDice;
      } else if (interactionKey.equals("habbowheel")) {
        item.interactor = Interactor.iterHabboWheel;
      } else if (interactionKey.equals("jukebox")) {
        item.interactor = Interactor.iterJukebox;
      } else if (interactionKey.equals("freezetimer")) {
        item.interactor = Interactor.iterTimer;
      } else if (interactionKey.equals("banzaicounter")) {
        item.interactor = Interactor.iterTimer;
      } else {
        item.interactor = Interactor.iterDefault;
      }
    }
  }
}
