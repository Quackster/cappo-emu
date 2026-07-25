package cappo.game.player.inventory;

import cappo.engine.Server;
import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.bots.RentalBot;
import cappo.game.collections.BaseItem;
import cappo.game.pets.Pet;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.Item;
import cappo.game.roomengine.entity.item.extradata.StuffDataReader;
import cappo.game.roomengine.entity.item.extradata.StuffDataWriter;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.roomengine.entity.item.wall.WallItem;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInventory
{
  public Connection cn;
  public static final int FURNIS = 1;
  public static final int PETS = 3;
  public static final int BOTS = 4;
  public static final int SONGS = 5;
  private Map<Integer, FloorItem> inventoryObjects;
  private Map<Integer, WallItem> inventoryItems;
  private Map<Integer, Pet> inventoryPets;
  private Map<Integer, RentalBot> inventoryBots;
  private Map<Integer, SongItem> SongInInventory;
  
  public PlayerInventory(Connection con)
  {
    this.cn = con;
  }
  
  public boolean isFull(int id)
  {
    if (id == 1)
    {
      initFurnis();
      return this.inventoryObjects.size() + this.inventoryItems.size() > 6000;
    }
    if (id == 3)
    {
      initPets();
      return this.inventoryPets.size() > 19;
    }
    if (id == 4)
    {
      initBots();
      return this.inventoryBots.size() > 149;
    }
    return true;
  }
  
  public boolean objectsReady()
  {
    return this.inventoryObjects != null;
  }
  
  public boolean itemsReady()
  {
    return this.inventoryItems != null;
  }
  
  public boolean petsReady()
  {
    return this.inventoryPets != null;
  }
  
  public boolean botsReady()
  {
    return this.inventoryBots != null;
  }
  
  public Collection<FloorItem> getObjectsForSave()
  {
    return this.inventoryObjects.values();
  }
  
  public Collection<WallItem> getItemsForSave()
  {
    return this.inventoryItems.values();
  }
  
  public Collection<Pet> getPetsForSave()
  {
    return this.inventoryPets.values();
  }
  
  public Collection<FloorItem> getObjects()
  {
    initFurnis();
    return this.inventoryObjects.values();
  }
  
  public Collection<WallItem> getItems()
  {
    initFurnis();
    return this.inventoryItems.values();
  }
  
  public Collection<SongItem> getSongs()
  {
    initFurnis();
    return this.SongInInventory.values();
  }
  
  public Collection<Pet> getPets()
  {
    initPets();
    return this.inventoryPets.values();
  }
  
  public Collection<RentalBot> getBots()
  {
    initBots();
    return this.inventoryBots.values();
  }
  
  public Item getFurni(int id)
  {
    initFurnis();
    Item item = (Item)this.inventoryObjects.get(Integer.valueOf(id));
    if (item == null) {
      item = (Item)this.inventoryItems.get(Integer.valueOf(id));
    }
    return item;
  }
  
  public FloorItem getObject(int id)
  {
    initFurnis();
    return (FloorItem)this.inventoryObjects.get(Integer.valueOf(id));
  }
  
  public WallItem getItem(int id)
  {
    initFurnis();
    return (WallItem)this.inventoryItems.get(Integer.valueOf(id));
  }
  
  public SongItem getSong(int id)
  {
    initFurnis();
    return (SongItem)this.SongInInventory.get(Integer.valueOf(id));
  }
  
  public Pet getPet(int id)
  {
    initPets();
    return (Pet)this.inventoryPets.get(Integer.valueOf(id));
  }
  
  public RentalBot getBot(int id)
  {
    initBots();
    return (RentalBot)this.inventoryBots.get(Integer.valueOf(id));
  }
  
  public void addObject(int id, FloorItem object)
  {
    initFurnis();
    this.inventoryObjects.put(Integer.valueOf(id), object);
  }
  
  public void addItem(int id, WallItem item)
  {
    initFurnis();
    this.inventoryItems.put(Integer.valueOf(id), item);
  }
  
  public void addSong(int id, SongItem song)
  {
    initFurnis();
    this.SongInInventory.put(Integer.valueOf(id), song);
  }
  
  public void addPet(int id, Pet pet)
  {
    initPets();
    this.inventoryPets.put(Integer.valueOf(id), pet);
  }
  
  public void addBot(int id, RentalBot bot)
  {
    initBots();
    this.inventoryBots.put(Integer.valueOf(id), bot);
  }
  
  public FloorItem removeObject(int id)
  {
    initFurnis();
    return (FloorItem)this.inventoryObjects.remove(Integer.valueOf(id));
  }
  
  public WallItem removeItem(int id)
  {
    initFurnis();
    return (WallItem)this.inventoryItems.remove(Integer.valueOf(id));
  }
  
  public SongItem removeSong(int id)
  {
    initFurnis();
    return (SongItem)this.SongInInventory.remove(Integer.valueOf(id));
  }
  
  public Pet removePet(int id)
  {
    initPets();
    return (Pet)this.inventoryPets.remove(Integer.valueOf(id));
  }
  
  public RentalBot removeBot(int id)
  {
    initPets();
    return (RentalBot)this.inventoryBots.remove(Integer.valueOf(id));
  }
  
  public void clearFurnis()
  {
    this.inventoryObjects = new ConcurrentHashMap();
    this.inventoryItems = new ConcurrentHashMap();
    this.SongInInventory = new ConcurrentHashMap();
  }
  
  private void initFurnis()
  {
    if (this.inventoryObjects != null) {
      return;
    }
    this.inventoryObjects = new ConcurrentHashMap();
    this.inventoryItems = new ConcurrentHashMap();
    this.SongInInventory = new ConcurrentHashMap();
    
    DBResult result = new DBResult();
    try
    {
      boolean ready = checkUpgradeBflyInventory(result);
      boolean old = ready;
      if (!ready) {
        loadInventory(result);
      }
      PlayerData playerData = this.cn.getPlayerData();
      while ((ready) || (result.data.next()))
      {
        ready = false;
        
        BaseItem base = (BaseItem)BaseItem.baseItems.get(Integer.valueOf(old ? result.data.getInt("base_id") : result.data.getInt("baseid")));
        if (base != null)
        {
          StuffDataReader data = new StuffDataReader(
            old ? BaseItem.upgradeStuffData(base, result.data.getString("data")).getData() : 
            result.data.getBytes("data"));
          
          int extraParam = old ? result.data.getInt("extra_param") : result.data.getInt("param");
          int itemId = old ? result.data.getInt("item_id") : result.data.getInt("id");
          try
          {
            if (base.Type.equals("s"))
            {
              FloorItem item = FloorItem.createItem(playerData, Server.generateRefItemId(), itemId, data, extraParam, base);
              this.cn.inventoryAddFloorItem(item);
              if (old) {
                item.setMysqlState(3);
              }
            }
            else if (base.Type.equals("i"))
            {
              WallItem item = WallItem.createItem(playerData, Server.generateRefItemId(), itemId, data, base);
              this.cn.inventoryAddWallItem(item);
              if (old) {
                item.setMysqlState(3);
              }
            }
          }
          catch (Exception ex)
          {
            Log.printException("SSO", ex);
          }
        }
      }
      if (old) {
        Database.exec(
        


          "DELETE da,db,dc FROM items_users AS da LEFT JOIN items_extradata AS db ON db.item_id=da.item_id LEFT JOIN items AS dc ON dc.item_id=da.item_id WHERE da.user_id = " + playerData.userId + ";", new Object[0]);
      }
    }
    catch (Exception ex)
    {
      Log.printException("PlayerInventory", ex);
    }
    result.close();
  }
  
  private boolean checkUpgradeBflyInventory(DBResult result)
    throws Exception
  {
    Database.query(result, "SELECT items.base_id,items_users.user_id,items_extradata.* FROM items_users LEFT JOIN items ON (items.item_id = items_users.item_id) LEFT JOIN items_extradata ON (items_extradata.item_id = items_users.item_id) WHERE items_users.user_id = " + 
    





      this.cn.getPlayerData().userId + ";", new Object[0]);
    
    return result.data.next();
  }
  
  private void loadInventory(DBResult result)
    throws Exception
  {
    Database.query(result, "SELECT furnis.*,furnis_floorextra.param FROM furnis LEFT JOIN furnis_floorextra ON (furnis_floorextra.id = furnis.id) WHERE furnis.userid=" + 
    



      this.cn.getPlayerData().userId + " AND furnis.roomid=0;", new Object[0]);
  }
  
  private void initPets()
  {
    if (this.inventoryPets != null) {
      return;
    }
    this.inventoryPets = new ConcurrentHashMap();
    
    DBResult result = new DBResult();
    try
    {
      PlayerData playerData = this.cn.getPlayerData();
      
      Database.query(result, "SELECT * FROM user_pets WHERE user_id = " + playerData.userId + " AND room_id = 0;", new Object[0]);
      while (result.data.next())
      {
        Pet pet = Clients.generatePetsData(result.data, playerData);
        if (pet != null) {
          this.inventoryPets.put(Integer.valueOf(pet.id), pet);
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("PlayerInventory", ex);
    }
    result.close();
  }
  
  private void initBots()
  {
    if (this.inventoryBots != null) {
      return;
    }
    this.inventoryBots = new ConcurrentHashMap();
    
    DBResult result = new DBResult();
    try
    {
      PlayerData playerData = this.cn.getPlayerData();
      
      Database.query(result, "SELECT * FROM user_bots WHERE user_id = " + playerData.userId + " AND room_id = 0;", new Object[0]);
      while (result.data.next())
      {
        RentalBot bot = Clients.generateBotsData(result.data, playerData);
        if (bot != null) {
          this.inventoryBots.put(Integer.valueOf(bot.id), bot);
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("PlayerInventory", ex);
    }
    result.close();
  }
  
  public void clean()
  {
    this.inventoryObjects = null;
    this.inventoryItems = null;
    this.inventoryPets = null;
    this.inventoryBots = null;
    this.SongInInventory = null;
  }
}


