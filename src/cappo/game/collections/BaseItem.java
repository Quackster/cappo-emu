/*   1:    */ package cappo.game.collections;
/*   2:    */ 
/*   3:    */ import cappo.engine.database.DBResult;
/*   4:    */ import cappo.engine.database.Database;
/*   5:    */ import cappo.game.catalog.giftwrapping.GiftWrappingConfiguration;
/*   6:    */ import cappo.game.roomengine.entity.item.extradata.StuffDataWriter;
/*   7:    */ import cappo.game.roomengine.itemInteractor.Interactor;
/*   8:    */ import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
/*   9:    */ import java.sql.ResultSet;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ 
/*  15:    */ public class BaseItem
/*  16:    */ {
/*  17:    */   public static final int CATEGORY_DEFAULT = 1;
/*  18:    */   public static final int CATEGORY_WALLPAPER = 2;
/*  19:    */   public static final int CATEGORY_FLOORSINGLE = 3;
/*  20:    */   public static final int CATEGORY_LANDSCAPE = 4;
/*  21:    */   public static final int CATEGORY_POSTIT = 5;
/*  22:    */   public static final int CATEGORY_POSTER = 6;
/*  23:    */   public static final int CATEGORY_SONGDISK_OLD = 7;
/*  24:    */   public static final int CATEGORY_SONGDISK = 8;
/*  25:    */   public static final int CATEGORY_PRESENT = 9;
/*  26:    */   public static final int CATEGORY_XMAS = 10;
/*  27:    */   public static final int CATEGORY_TROPHY = 11;
/*  28:    */   public static final int CATEGORY_HORSE_SHAMPOO = 13;
/*  29:    */   public static final int CATEGORY_HORSE_HAIR_STYLE = 14;
/*  30:    */   public static final int CATEGORY_HORSE_HAIR_SHAMPOO = 15;
/*  31:    */   public static final int CATEGORY_SADDLE = 16;
/*  32:    */   public static final int CATEGORY_GROUPFURNIS = 17;
/*  33:    */   public static final int CATEGORY_SNOWWAR = 18;
/*  34:    */   public static final int CATEGORY_MONSTERPLANT_SEED = 19;
/*  35:    */   public static final int CATEGORY_MONSTERPLANT_REVIVAL = 20;
/*  36:    */   public static final int CATEGORY_MONSTERPLANT_REBREED = 21;
/*  37:    */   public static final int CATEGORY_MONSTERPLANT_FERTILIZE = 22;
/*  38:    */   public static final int WIRED_EFFECT_SHOWMESSAGE = 7;
/*  39:    */   public static final int WIRED_EFFECT_TELEPORT = 13;
/*  40:    */   public static final int WIRED_EFFECT_MOVEANDROTATE = 16;
/*  41:    */   
/*  42:    */   public static enum ItemType
/*  43:    */   {
/*  44: 30 */     WIRED_TRIGGER,  WIRED_EFFECT,  WIRED_CONDITION,  ROOMGAME_GATE,  ROOMGAME_SCORE,  FOOTBALL_GOAL,  WATER;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static enum FurniLogic
/*  48:    */   {
/*  49: 41 */     BASIC,  MULTISTATE,  CRACKABLE,  MANNEQUIN,  ROOMDIMMER,  BADGEDISPLAY,  MULTIHEIGHT,  FLOORHOLE;
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static FurniLogic getLogic(String furniLogic)
/*  53:    */   {
/*  54: 46 */     if (furniLogic.equals("furniture_multistate")) {
/*  55: 47 */       return FurniLogic.MULTISTATE;
/*  56:    */     }
/*  57: 49 */     if (furniLogic.equals("furniture_crackable")) {
/*  58: 50 */       return FurniLogic.CRACKABLE;
/*  59:    */     }
/*  60: 52 */     if (furniLogic.equals("furniture_mannequin")) {
/*  61: 53 */       return FurniLogic.MANNEQUIN;
/*  62:    */     }
/*  63: 55 */     if (furniLogic.equals("furniture_roomdimmer")) {
/*  64: 56 */       return FurniLogic.ROOMDIMMER;
/*  65:    */     }
/*  66: 58 */     if (furniLogic.equals("furniture_badge_display")) {
/*  67: 59 */       return FurniLogic.BADGEDISPLAY;
/*  68:    */     }
/*  69: 61 */     if (furniLogic.equals("furniture_multiheight")) {
/*  70: 62 */       return FurniLogic.MULTIHEIGHT;
/*  71:    */     }
/*  72: 64 */     if (furniLogic.equals("furniture_floor_hole")) {
/*  73: 65 */       return FurniLogic.FLOORHOLE;
/*  74:    */     }
/*  75: 70 */     return FurniLogic.BASIC;
/*  76:    */   }
/*  77:    */   
/*  78:104 */   public static final BaseItem snst_tree1_d = new BaseItem("s", 4061, "snst_tree1_d", 1, 1);
/*  79:105 */   public static final BaseItem snst_block1 = new BaseItem("s", 4066, "snst_block1", 1, 1);
/*  80:106 */   public static final BaseItem snst_ballpile = new BaseItem("s", 4059, "snst_ballpile", 1, 1);
/*  81:107 */   public static final BaseItem xm09_man_a = new BaseItem("s", 3038, "xm09_man_a", 1, 1);
/*  82:108 */   public static final BaseItem xm09_man_c = new BaseItem("s", 3032, "xm09_man_c", 1, 1);
/*  83:109 */   public static final BaseItem xm09_man_b = new BaseItem("s", 3037, "xm09_man_b", 1, 1);
/*  84:110 */   public static final BaseItem snst_fence = new BaseItem("s", 4062, "snst_fence", 1, 2);
/*  85:111 */   public static final BaseItem ads_background = new BaseItem("s", 3704, "ads_background", 1, 1);
/*  86:112 */   public static final BaseItem snst_tree1 = new BaseItem("s", 4063, "snst_tree1", 1, 1);
/*  87:113 */   public static final BaseItem s_snowball_machine = new BaseItem("s", 4068, "s_snowball_machine", 1, 1);
/*  88:114 */   public static final BaseItem snst_iceblock = new BaseItem("s", 4064, "snst_iceblock", 1, 1);
/*  89:115 */   public static final BaseItem ads_igorraygun = new BaseItem("s", 2648, "ads_igorraygun", 1, 2);
/*  90:    */   
/*  91:    */   public BaseItem(String type, int id, String name, int xdim, int ydim)
/*  92:    */   {
/*  93:118 */     this.Type = type;
/*  94:119 */     this.SpriteId = id;
/*  95:120 */     this.Name = name;
/*  96:121 */     this.xDim = xdim;
/*  97:122 */     this.yDim = ydim;
/*  98:123 */     this.Height = 1.0F;
/*  99:    */   }
/* 100:    */   
/* 101:127 */   public static Map<Integer, BaseItem> baseItems = new HashMap(5000);
/* 102:    */   public int Id;
/* 103:    */   public int SpriteId;
/* 104:    */   public String Name;
/* 105:    */   public String Type;
/* 106:    */   public int xDim;
/* 107:    */   public int yDim;
/* 108:    */   public float Height;
/* 109:    */   public boolean AllowStack;
/* 110:    */   public boolean allowWalk;
/* 111:    */   public boolean allowLay;
/* 112:    */   public boolean allowSit;
/* 113:    */   public boolean AllowRecycle;
/* 114:    */   public boolean AllowTrade;
/* 115:    */   public boolean AllowMarketplaceSell;
/* 116:    */   public boolean AllowGift;
/* 117:    */   public boolean AllowInventoryStack;
/* 118:    */   public List<Integer> vendingIds;
/* 119:    */   public int interactionCount;
/* 120:    */   public Interactor interactor;
/* 121:    */   public Interactor.InteractorType interactorType;
/* 122:    */   public ItemType itemType;
/* 123:    */   public FurniLogic logic;
/* 124:156 */   public int itemExtraType = 0;
/* 125:158 */   public int itemCategory = 1;
/* 126:    */   
/* 127:    */   public BaseItem() {}
/* 128:    */   
/* 129:    */   public static StuffDataWriter upgradeStuffData(BaseItem base, String extra)
/* 130:    */   {
/* 131:163 */     StuffDataWriter data = null;
/* 132:164 */     if (base.itemExtraType == 0)
/* 133:    */     {
/* 134:165 */       data = new StuffDataWriter(0);
/* 135:166 */       data.writeString(extra == null ? "" : extra);
/* 136:    */     }
/* 137:167 */     else if (base.itemExtraType == 2)
/* 138:    */     {
/* 139:168 */       data = new StuffDataWriter(2);
/* 140:169 */       data.writeInt8(((Integer)data.setSaved(Integer.valueOf(0))).intValue());
/* 141:    */       
/* 142:171 */       int size = 0;
/* 143:172 */       if (extra != null)
/* 144:    */       {
/* 145:173 */         String[] parts = extra.split(";");
/* 146:174 */         for (String val : parts)
/* 147:    */         {
/* 148:175 */           data.writeString(val);
/* 149:176 */           size++;
/* 150:    */         }
/* 151:    */       }
/* 152:179 */       data.writeSavedInt8(size);
/* 153:    */     }
/* 154:180 */     else if (base.itemExtraType == 1)
/* 155:    */     {
/* 156:181 */       data = new StuffDataWriter(1);
/* 157:182 */       data.writeInt8(((Integer)data.setSaved(Integer.valueOf(0))).intValue());
/* 158:    */       
/* 159:184 */       int size = 0;
/* 160:185 */       if (extra != null)
/* 161:    */       {
/* 162:186 */         String[] values = extra.split("\t");
/* 163:187 */         for (String part : values) {
/* 164:188 */           if ((!part.isEmpty()) && (!part.equals("=")))
/* 165:    */           {
/* 166:192 */             String[] a = part.split("=");
/* 167:193 */             if (a.length == 2)
/* 168:    */             {
/* 169:197 */               data.writeString(a[0]);
/* 170:198 */               data.writeString(a[1]);
/* 171:199 */               size++;
/* 172:    */             }
/* 173:    */           }
/* 174:    */         }
/* 175:    */       }
/* 176:202 */       data.writeSavedInt8(size);
/* 177:    */     }
/* 178:203 */     else if (base.itemExtraType == 5)
/* 179:    */     {
/* 180:204 */       data = new StuffDataWriter(5);
/* 181:205 */       data.writeInt8(((Integer)data.setSaved(Integer.valueOf(0))).intValue());
/* 182:    */       
/* 183:207 */       int size = 0;
/* 184:208 */       if (extra != null)
/* 185:    */       {
/* 186:209 */         String[] parts = extra.split(";");
/* 187:210 */         for (String val : parts)
/* 188:    */         {
/* 189:211 */           data.writeInt32(Integer.parseInt(val));
/* 190:212 */           size++;
/* 191:    */         }
/* 192:    */       }
/* 193:215 */       data.writeSavedInt8(size);
/* 194:    */     }
/* 195:216 */     else if (base.itemExtraType == 3)
/* 196:    */     {
/* 197:217 */       data = new StuffDataWriter(3);
/* 198:218 */       data.writeString(extra == null ? "" : extra);
/* 199:219 */       data.writeInt16(0);
/* 200:    */     }
/* 201:224 */     return data;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static void Init(DBResult result)
/* 205:    */     throws Exception
/* 206:    */   {
/* 207:228 */     GiftWrappingConfiguration.baseGiftItems.clear();
/* 208:229 */     baseItems.clear();
/* 209:    */     
/* 210:231 */     Database.query(result, "SELECT * FROM furnis_base;", new Object[0]);
/* 211:232 */     while (result.data.next()) {
/* 212:233 */       GenerateFurniture(result.data);
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   private static void GenerateFurniture(ResultSet userdata)
/* 217:    */     throws Exception
/* 218:    */   {
/* 219:238 */     BaseItem item = new BaseItem();
/* 220:    */     
/* 221:240 */     item.Id = userdata.getInt("baseid");
/* 222:241 */     baseItems.put(Integer.valueOf(item.Id), item);
/* 223:    */     
/* 224:243 */     item.SpriteId = userdata.getInt("id");
/* 225:244 */     item.Name = userdata.getString("classname");
/* 226:    */     
/* 227:246 */     int furniType = userdata.getInt("type");
/* 228:247 */     item.Type = (furniType == 5 ? "h" : furniType == 4 ? "r" : furniType == 3 ? "e" : furniType == 2 ? "i" : furniType == 1 ? "s" : "");
/* 229:    */     
/* 230:249 */     item.xDim = userdata.getInt("xdim");
/* 231:250 */     item.yDim = userdata.getInt("ydim");
/* 232:251 */     item.Height = userdata.getFloat("height");
/* 233:252 */     item.allowWalk = (userdata.getInt("canstandon") == 1);
/* 234:253 */     item.allowLay = (userdata.getInt("canlayon") == 1);
/* 235:254 */     item.allowSit = ((userdata.getInt("cansiton") == 1) || (item.allowLay));
/* 236:255 */     item.AllowStack = (userdata.getInt("allow_stack") == 1);
/* 237:    */     
/* 238:257 */     item.itemCategory = userdata.getInt("specialtype");
/* 239:    */     
/* 240:259 */     item.AllowRecycle = (userdata.getInt("allow_recycle") == 1);
/* 241:260 */     item.AllowTrade = (userdata.getInt("allow_trade") == 1);
/* 242:261 */     item.AllowMarketplaceSell = (userdata.getInt("allow_marketplace_sell") == 1);
/* 243:262 */     item.AllowGift = (userdata.getInt("allow_gift") == 1);
/* 244:263 */     item.AllowInventoryStack = (userdata.getInt("allow_inventory_stack") == 1);
/* 245:264 */     String tmp = userdata.getString("vending_ids");
/* 247:265 */     if (!tmp.isEmpty())
/* 248:    */     {
/* 249:266 */       String[] values = tmp.split(",");
/* 250:267 */       item.vendingIds = new ArrayList(values.length);
/* 251:268 */       for (String val : values) {
/* 252:269 */         item.vendingIds.add(Integer.valueOf(Integer.parseInt(val.trim())));
/* 253:    */       }
/* 254:    */     }
/* 255:273 */     item.logic = getLogic(userdata.getString("furni_logic"));
/* 256:    */     
/* 257:275 */     item.itemExtraType = userdata.getInt("extradata_type");
/* 258:276 */     item.interactionCount = userdata.getInt("cycle_count");
/* 259:277 */     String interactionKey = userdata.getString("interaction_type");
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
/* 435:349 */     if (item.interactorType == null)
/* 436:    */     {
/* 437:350 */       item.interactorType = Interactor.GetInteractorType(interactionKey);
/* 438:351 */       if (item.interactorType == Interactor.InteractorType.gift) {
/* 439:352 */         GiftWrappingConfiguration.addGift(item);
/* 440:    */       }
/* 441:355 */       if (item.logic == FurniLogic.MANNEQUIN) {
/* 442:356 */         item.interactor = Interactor.iterOutfit;
/* 443:357 */       } else if (interactionKey.equals("teleport")) {
/* 444:358 */         item.interactor = Interactor.iterTeleport;
/* 445:359 */       } else if (interactionKey.equals("vendingmachine")) {
/* 446:360 */         item.interactor = Interactor.iterVendingMachine;
/* 447:361 */       } else if (interactionKey.equals("onewaygate")) {
/* 448:362 */         item.interactor = Interactor.iterOneWayGate;
/* 449:363 */       } else if (interactionKey.equals("dice")) {
/* 450:364 */         item.interactor = Interactor.iterDice;
/* 451:365 */       } else if (interactionKey.equals("habbowheel")) {
/* 452:366 */         item.interactor = Interactor.iterHabboWheel;
/* 453:367 */       } else if (interactionKey.equals("jukebox")) {
/* 454:368 */         item.interactor = Interactor.iterJukebox;
/* 455:369 */       } else if (interactionKey.equals("freezetimer")) {
/* 456:370 */         item.interactor = Interactor.iterTimer;
/* 457:371 */       } else if (interactionKey.equals("banzaicounter")) {
/* 458:372 */         item.interactor = Interactor.iterTimer;
/* 459:    */       } else {
/* 460:374 */         item.interactor = Interactor.iterDefault;
/* 461:    */       }
/* 462:    */     }
/* 463:    */   }
/* 464:    */ }


/* Location:           C:\Users\Manel\Downloads\cappo.zip
 * Qualified Name:     cappo.game.collections.BaseItem
 * JD-Core Version:    0.7.0.1
 */