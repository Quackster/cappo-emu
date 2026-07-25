package cappo.engine.threadpools;

import cappo.engine.Server;
import cappo.engine.ServerProps;
import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.bots.RentalBot;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BaseItem.FurniLogic;
import cappo.game.collections.BaseItem.ItemType;
import cappo.game.collections.BflyData;
import cappo.game.collections.MoodlightData;
import cappo.game.collections.Teleports;
import cappo.game.collections.Utils;
import cappo.game.games.snowwar.Direction8;
import cappo.game.inventory.trading.Trade;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.navigator.NavigatorCategories;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.player.messenger.MessengerFriend;
import cappo.game.player.messenger.MessengerFriendUpdate;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.game.roomeffects.special.UserSpecialEffect;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomIcon;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.Square;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.extradata.StuffDataReader;
import cappo.game.roomengine.entity.item.extradata.StuffDataWriter;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.floor.RollerItem;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData.AffectedTile;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.roomengine.entity.item.floor.wired.WiredItemBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.item.wall.RoomWallItemData;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.LiveEntity;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.game.roomengine.entity.live.RentalBotEntity;
import cappo.game.roomengine.gamemap.CustomGameMap;
import cappo.game.roomengine.gamemap.DynamicGameMap;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.game.roomengine.itemInteractor.InteractorTeleport.TeleportAttach;
import cappo.game.roomengine.roomevents.Event;
import cappo.game.roomengine.roomevents.RollerEvent;
import cappo.game.roomengine.roomevents.Teleport_OUT;
import cappo.game.roomengine.roomevents.UserChat;
import cappo.game.roomengine.roomlisting.RoomListing;
import cappo.game.roomengine.roomlisting.RoomListing.ListingRoomState;
import cappo.game.roomengine.settings.ChatSettings;
import cappo.game.roomengine.settings.ControllerLevels;
import cappo.game.roomengine.settings.ModerationPermissions;
import cappo.game.roomengine.settings.PlayerBan;
import cappo.game.roomengine.settings.PlayerRight;
import cappo.game.roomengine.settings.TradingSettings;
import cappo.game.roomengine.wired.WiredManager;
import cappo.game.sound.trax.TraxPlaylist;
import cappo.protocol.messages.composers.handshake.GenericErrorComposer;
import cappo.protocol.messages.composers.inventory.pets.AddPetToInventoryComposer;
import cappo.protocol.messages.composers.inventory.trading.TradingCloseComposer;
import cappo.protocol.messages.composers.navigator.DoorBellNoAnswerComposer;
import cappo.protocol.messages.composers.navigator.DoorbellUserComposer;
import cappo.protocol.messages.composers.navigator.EventComposer;
import cappo.protocol.messages.composers.navigator.FlatAccessDeniedComposer;
import cappo.protocol.messages.composers.navigator.RoomRatingComposer;
import cappo.protocol.messages.composers.room.action.UserAsleepComposer;
import cappo.protocol.messages.composers.room.engine.ItemAddComposer;
import cappo.protocol.messages.composers.room.engine.ItemRemoveComposer;
import cappo.protocol.messages.composers.room.engine.ItemUpdateComposer;
import cappo.protocol.messages.composers.room.engine.ObjectAddComposer;
import cappo.protocol.messages.composers.room.engine.ObjectDataUpdateComposer;
import cappo.protocol.messages.composers.room.engine.ObjectRemoveComposer;
import cappo.protocol.messages.composers.room.engine.ObjectUpdateComposer;
import cappo.protocol.messages.composers.room.engine.ObjectsDataUpdateComposer;
import cappo.protocol.messages.composers.room.engine.RoomPropertyComposer;
import cappo.protocol.messages.composers.room.engine.UserRemoveComposer;
import cappo.protocol.messages.composers.room.engine.UserUpdateComposer;
import cappo.protocol.messages.composers.room.engine.UsersComposer;
import cappo.protocol.messages.composers.room.permissions.YouAreControllerComposer;
import cappo.protocol.messages.composers.room.permissions.YouAreNotControllerComposer;
import cappo.protocol.messages.composers.room.permissions.YouAreOwnerComposer;
import cappo.protocol.messages.composers.room.session.CloseConnectionComposer;
import cappo.protocol.messages.composers.room.session.OpenConnectionComposer;
import cappo.protocol.messages.composers.room.session.RoomReadyComposer;
import cappo.protocol.messages.composers.room.session.YouArePlayingGameComposer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

public class RoomTask
  extends GameTask
{
  public RoomData roomData;
  public int roomId;
  public GameMapBase model;
  public Map<Integer, Avatar> userList;
  
  public static void addTask(GameTask task, int initDelay, int repeatDelay)
  {
    WorkerTasks.addTask(task, initDelay, repeatDelay, WorkerTasks.RoomsTasks);
  }
  
  public final Map<Integer, PetEntity> petList = new ConcurrentHashMap(5);
  public final Map<Integer, RentalBotEntity> rentalBotList = new ConcurrentHashMap(5);
  private Map<Integer, Event> userEvents;
  private Integer eventIdGeneratorUsers;
  public Map<Integer, PlayerRight> usersWithRights;
  public Map<Integer, PlayerBan> usersBanned;
  public final List<GenericFloorItem> roomGamesScorersRED = new ArrayList(3);
  public final List<GenericFloorItem> roomGamesScorersGREEN = new ArrayList(3);
  public final List<GenericFloorItem> roomGamesScorersBLUE = new ArrayList(3);
  public final List<GenericFloorItem> roomGamesScorersYELLOW = new ArrayList(3);
  public Map<Integer, Direction8> MatrixRot;
  public Map<Integer, FloorItem> FloorItems;
  public Map<Integer, GenericWallItem> WallItems;
  public short VirtualIdCounter = 1;
  private final Set<FloorItem> UpdateFloorItems = new HashSet(50);
  private final Set<WallItem> UpdateWallItems = new HashSet(50);
  private final Map<Short, LiveEntity> UpdateUsers = new ConcurrentHashMap();
  public Map<Integer, Float> squareAbsoluteHeight;
  public Map<Integer, FloorItem> topFloorItems;
  public Map<Integer, Map<Integer, FloorItem>> mapFloorItems;
  public Map<Integer, RollerItem> rollers;
  public SquareFlagManager squareFlag;
  public MoodlightData MoodlightData;
  public WiredManager wiredManager;
  public int userCount;
  public int PetCounter;
  public int ScorePoints_B;
  public int ScorePoints_G;
  public int ScorePoints_R;
  public int ScorePoints_Y;
  public final TraxPlaylist traxPlaylist = new TraxPlaylist();
  public final RoomListing.ListingRoomState[] roomListingState;
  public Map<Integer, Map<Short, LiveEntity>> usersMatrix;
  private int timeOut;
  
  public boolean squareHasUsers(int xy)
  {
    Map<Short, LiveEntity> users = (Map)this.usersMatrix.get(Integer.valueOf(xy));
    if ((users == null) || (users.isEmpty())) {
      return false;
    }
    return true;
  }
  
  public void entityWalk(int xy, LiveEntity ent, boolean add)
  {
    Map<Short, LiveEntity> users = (Map)this.usersMatrix.get(Integer.valueOf(xy));
    if (users == null)
    {
      if (!add) {
        return;
      }
      users = new ConcurrentHashMap();
      this.usersMatrix.put(Integer.valueOf(xy), users);
    }
    if (add)
    {
      users.put(Short.valueOf(ent.virtualId), ent);
    }
    else
    {
      users.remove(Short.valueOf(ent.virtualId));
      if (users.isEmpty()) {
        this.usersMatrix.remove(Integer.valueOf(xy));
      }
    }
  }
  
  public RoomTask(RoomData self)
  {
    this.roomData = self;
    this.roomId = self.roomId;
    this.model = RoomManager.getModel(self.model);
    
    this.roomListingState = new RoomListing.ListingRoomState[2 + (1 + NavigatorCategories.MAX_ID)];
  }
  
  private boolean checkUpgradeBflyFurnis(DBResult result)
    throws Exception
  {
    Database.query(result, "SELECT items.base_id,items_rooms.*,items_extradata.* FROM items_rooms LEFT JOIN items ON (items.item_id = items_rooms.item_id) LEFT JOIN items_extradata ON (items_extradata.item_id = items_rooms.item_id) WHERE items_rooms.room_id = " + 
    





      this.roomId + ";", new Object[0]);
    
    return result.data.next();
  }
  
  public void init()
  {
    PlayerData roomOwner = Clients.getPlayerData(this.roomData.roomOwnerId);
    if (roomOwner == null)
    {
      roomOwner = Clients.getPlayerData(this.roomData.roomOwnerName);
      if (roomOwner == null)
      {
        Log.printLog("loadRoomResultSet, owner=NULL, id=" + this.roomData.roomId);
        return;
      }
      this.roomData.roomOwnerId = roomOwner.userId;
      try
      {
        Database.exec("UPDATE `rooms` SET `user_id`='" + roomOwner.userId + "' WHERE id='" + this.roomData.roomId + "';", new Object[0]);
      }
      catch (Exception ex)
      {
        Log.printException("loadRoomResultSet", ex);
      }
    }
    if (!roomOwner.userName.equals(this.roomData.roomOwnerName))
    {
      this.roomData.roomOwnerName = roomOwner.userName;
      try
      {
        Database.exec("UPDATE `rooms` SET `user_name`='" + roomOwner.userName + "' WHERE id='" + this.roomData.roomId + "';", new Object[0]);
      }
      catch (Exception ex)
      {
        Log.printException("loadRoomResultSet", ex);
      }
    }
    this.roomData.roomOwner = roomOwner;
    
    this.userList = new ConcurrentHashMap(this.roomData.usersMax);
    
    this.rollers = new ConcurrentHashMap();
    
    this.wiredManager = new WiredManager();
    this.MatrixRot = new ConcurrentHashMap();
    this.WallItems = new ConcurrentHashMap(10);
    this.FloorItems = new ConcurrentHashMap(100);
    this.usersMatrix = new ConcurrentHashMap();
    
    this.userEvents = new ConcurrentHashMap();
    
    this.eventIdGeneratorUsers = Integer.valueOf(0);
    
    DBResult result = new DBResult();
    DBResult resultInternal = new DBResult();
    DBResult resultInternal2 = new DBResult();
    try
    {
      Map<FloorItem, List<RoomFloorItemData.AffectedTile>> tmpFloorItems = new HashMap();
      int maxX = this.model.widthX - 1;int maxY = this.model.heightY - 1;
      
      boolean ready = checkUpgradeBflyFurnis(result);
      boolean old = ready;
      if (!ready) {
        loadFurnis(result);
      }
      int itemOwnerId;
      int extraParam;
      while ((ready) || (result.data.next()))
      {
        ready = false;
        try
        {
          BaseItem base = (BaseItem)BaseItem.baseItems.get(Integer.valueOf(old ? result.data.getInt("base_id") : result.data.getInt("baseid")));
          if (base != null)
          {
            StuffDataReader data = new StuffDataReader(
              old ? BaseItem.upgradeStuffData(base, result.data.getString("data")).getData() : 
              result.data.getBytes("data"));
            
            itemOwnerId = old ? 0 : result.data.getInt("userid");
            int itemId = old ? result.data.getInt("item_id") : result.data.getInt("id");
            extraParam = old ? result.data.getInt("extra_param") : result.data.getInt("param");
            
            float x = old ? result.data.getFloat("x") : result.data.getFloat("a");
            float y = old ? result.data.getFloat("y") : result.data.getFloat("b");
            int n = old ? result.data.getInt("n") : result.data.getInt("r");
            PlayerData itemOwner;
            if ((this.roomData.roomOwner != null) && (this.roomData.roomOwner.userId == itemOwnerId))
            {
              itemOwner = this.roomData.roomOwner;
            }
            else
            {
              itemOwner = Clients.getPlayerDataLoaded(itemOwnerId);
              if (itemOwner == null) {
                itemOwner = this.roomData.roomOwner;
              } else if (this.roomData.roomOwner == null) {
                this.roomData.roomOwner = itemOwner;
              }
            }
            if (base.Type.equals("s"))
            {
              int refId = Server.generateRefItemId();
              FloorItem roomItem = FloorItem.createItem(itemOwner, refId, itemId, data, extraParam, base);
              if (old) {
                roomItem.setMysqlState(3);
              }
              if ((roomItem.baseItem.interactor != Interactor.iterWired) || 
                ((roomItem instanceof WiredItemBase)))
              {
                int iY = BflyData.getB(x);
                int iX = BflyData.getA(x, iY);
                roomItem.setRoomData(new RoomFloorItemData(this, roomItem));
                roomItem.setPosition(iX, iY);
                roomItem.setPosition(y);
                roomItem.setDir(Direction8.getDirection(n));
                
                List<RoomFloorItemData.AffectedTile> tiles = roomItem.getAffectedTiles(true);
                
                boolean invalid = false;
                for (RoomFloorItemData.AffectedTile tile : tiles)
                {
                  if (tile.x > maxX) {
                    maxX = tile.x;
                  }
                  if (tile.y > maxY) {
                    maxY = tile.y;
                  }
                  if ((tile.x == this.model.doorX) && (tile.y == this.model.doorY)) {
                    invalid = true;
                  }
                }
                if (!invalid)
                {
                  if (roomItem.baseItem.interactorType == Interactor.InteractorType.roller) {
                    this.rollers.put(Integer.valueOf(roomItem.itemId), (RollerItem)roomItem);
                  }
                  tmpFloorItems.put(roomItem, tiles);
                }
              }
            }
            else if (base.Type.equals("i"))
            {
              int refId = Server.generateRefItemId();
              WallItem wallItem = WallItem.createItem(itemOwner, refId, itemId, data, base);
              if (old) {
                wallItem.setMysqlState(3);
              }
              if ((wallItem instanceof GenericWallItem))
              {
                GenericWallItem roomItem = (GenericWallItem)wallItem;
                if (roomItem.baseItem.logic == BaseItem.FurniLogic.ROOMDIMMER)
                {
                  if (this.MoodlightData == null)
                  {
                    this.MoodlightData = new MoodlightData(roomItem.itemId);
                    roomItem.extraData.setExtraData(this.MoodlightData.GenerateExtraData());
                  }
                }
                else
                {
                  roomItem.setRoomData(new RoomWallItemData(this, roomItem.baseItem, x, y, n));
                  this.WallItems.put(Integer.valueOf(roomItem.itemId), roomItem);
                }
              }
            }
          }
        }
        catch (Exception ex)
        {
          Log.printException("Room", ex);
        }
      }
      if (old) {
        Database.exec(
        


          "DELETE da,db,dc FROM items_rooms AS da LEFT JOIN items_extradata AS db ON db.item_id=da.item_id LEFT JOIN items AS dc ON dc.item_id=da.item_id WHERE da.room_id = " + this.roomId + ";", new Object[0]);
      }
      if ((maxX >= this.model.widthX) || (maxY >= this.model.heightY))
      {
        GameMapBase exModel = this.model;
        
        this.model = new DynamicGameMap(this.model, maxX + 1, maxY + 1);
        for (List<RoomFloorItemData.AffectedTile> tiles : tmpFloorItems.values())
        {
          for (RoomFloorItemData.AffectedTile tile : tiles)
          {
/*  408b:    */             if ((tile.x >= exModel.widthX) || (tile.y >= exModel.heightY))
            {
              int pos = tile.x + tile.y * this.model.widthX;
              this.model.setSquare(pos, new Square(tile.x, tile.y, pos, 0.0F));
            }
/*  412b:    */           }
        }
        this.model.buildSquares();
      }
      this.squareFlag = new SquareFlagManager();
      this.squareAbsoluteHeight = new ConcurrentHashMap();
      this.topFloorItems = new ConcurrentHashMap();
      this.mapFloorItems = new ConcurrentHashMap();
      
      int xy = 0;
      int x;
      for (int y = 0; y < this.model.heightY; y++) {
        for (x = 0; x < this.model.widthX; x++)
        {
          Square sq = this.model.getSquare(xy);
          if ((x == this.model.doorX) && (y == this.model.doorY))
          {
            this.squareAbsoluteHeight.put(Integer.valueOf(xy), Float.valueOf(sq.height));
            this.squareFlag.SetFlag(xy, 8, true);
          }
          else if (sq != null)
          {
            this.squareAbsoluteHeight.put(Integer.valueOf(xy), Float.valueOf(sq.height));
          }
          xy++;
        }
      }
      for (FloorItem roomItem : tmpFloorItems.keySet())
      {
        roomItem.setPosition();
        roomItem.finishPlace((List)tmpFloorItems.get(roomItem));
      }
      for (FloorItem roomItem : tmpFloorItems.keySet()) {
        if (roomItem.baseItem.interactor == Interactor.iterWired)
        {
          WiredItemBase wired = (WiredItemBase)roomItem;
          wired.loadData(resultInternal);
        }
        else if ((roomItem.baseItem.interactorType == Interactor.InteractorType.teleport) && 
          (!Teleports.teleLoaded(roomItem.itemId)))
        {
          Teleports.setRoom(roomItem.itemId, this.roomId);
          Database.query(resultInternal, "SELECT * FROM items_tele_links WHERE tele_one_id=" + roomItem.itemId + " OR tele_two_id=" + roomItem.itemId + " LIMIT 1;", new Object[0]);
          if (resultInternal.data.next())
          {
            int tl1 = resultInternal.data.getInt("tele_one_id");
            int otherId;
            if (tl1 != roomItem.itemId)
            {
              Teleports.setParents(tl1, roomItem.itemId);
              otherId = tl1;
            }
            else
            {
              int tl2 = resultInternal.data.getInt("tele_two_id");
              Teleports.setParents(roomItem.itemId, tl2);
              otherId = tl2;
            }
            Database.query(resultInternal2, "SELECT roomid FROM furnis WHERE id=" + otherId + " LIMIT 1;", new Object[0]);
            if (resultInternal2.data.next()) {
              Teleports.setRoom(otherId, resultInternal2.data.getInt("roomid"));
            }
          }
        }
      }
      Database.query(result, "SELECT * FROM user_pets WHERE room_id = " + this.roomId + " LIMIT 1;", new Object[0]);
      while (result.data.next())
      {
        Pet pet = Clients.generatePetsData(result.data, this.roomData.roomOwner);
        if (pet != null) {
          deployPet(pet, result.data.getInt("x") + result.data.getInt("y") * this.model.widthX, result.data.getInt("z"));
        }
      }
      Database.query(result, "SELECT * FROM user_bots WHERE room_id = " + this.roomId + " LIMIT 1;", new Object[0]);
      while (result.data.next())
      {
        RentalBot bot = Clients.generateBotsData(result.data, this.roomData.roomOwner);
        if (bot != null) {
          deployBot(bot, 
            result.data.getInt("x") + result.data.getInt("y") * this.model.widthX, 
            result.data.getInt("z"), 
            new AvatarLook(result.data.getString("look")), 
            result.data.getString("gender"));
        }
      }
      Database.query(result, "SELECT songid,itemid FROM room_discs WHERE roomid = " + this.roomId + ";", new Object[0]);
      while (result.data.next())
      {
        int songDisc = result.data.getInt("songid");
        if (songDisc > 0)
        {
          SongItem song = new SongItem();
          song.itemId = result.data.getInt("itemid");
          song.baseItem = ((BaseItem)BaseItem.baseItems.get(Integer.valueOf(2964)));
          song.owner = this.roomData.roomOwner;
          song.setExtraParam(songDisc);
          this.traxPlaylist.PlaylistByIndex.add(song);
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("Room-LoadRoom", ex);
    }
    resultInternal.close();
    resultInternal2.close();
    result.close();
    

    int xy = 0;
    for (int y = 0; y < this.model.heightY; y++) {
      for (int x = 0; x < this.model.widthX; x++)
      {
        if ((this.model.getSquare(xy) != null) && 
          (this.mapFloorItems.get(Integer.valueOf(xy)) == null)) {
          this.squareFlag.SetFlag(xy, 4, true);
        }
        xy++;
      }
    }
    RoomManager.setActive();
  }
  
  private void loadFurnis(DBResult result)
    throws Exception
  {
    Database.query(result, "SELECT furnis.*,furnis_floorextra.param,furnis_roomdata.a,furnis_roomdata.b,furnis_roomdata.r FROM furnis LEFT JOIN furnis_roomdata ON (furnis_roomdata.id = furnis.id) LEFT JOIN furnis_floorextra ON (furnis_floorextra.id = furnis.id) WHERE furnis.roomid = " + 
    







      this.roomId + ";", new Object[0]);
  }
  
  public void addBan(PlayerData player, int seconds)
  {
    this.usersBanned.put(Integer.valueOf(player.userId), new PlayerBan(player, Utils.getTimestamp() + seconds));
  }
  
  public PlayerBan removeBan(int userId)
  {
    return (PlayerBan)this.usersBanned.remove(Integer.valueOf(userId));
  }
  
  public boolean hasBanExpired(int Id)
  {
    PlayerBan ban = (PlayerBan)this.usersBanned.get(Integer.valueOf(Id));
    if ((ban != null) && (Utils.getTimestamp() > ban.timeout))
    {
      this.usersBanned.remove(Integer.valueOf(Id));
      return true;
    }
    return false;
  }
  
  public boolean userIsBanned(int Id)
  {
    return this.usersBanned.containsKey(Integer.valueOf(Id));
  }
  
  public void updateMaxUsers(int size)
  {
    if (this.userList == null)
    {
      this.userList = new ConcurrentHashMap(size);
    }
    else
    {
      if (this.userList.size() > size) {
        return;
      }
      Map<Integer, Avatar> tmp = this.userList;
      this.userList = new ConcurrentHashMap(size);
      this.userList.putAll(tmp);
    }
  }
  
  public void sendMessage(MessageWriter message)
  {
    for (Avatar User : this.userList.values()) {
      QueueWriter.writeAndFlush(User.cn.socket, message);
    }
  }
  
  public void sendMessage(MessageWriter message, int... needRights)
  {
    if (needRights.length == 0) {
      sendMessage(message);
    } else {
      for (Avatar User : this.userList.values()) {
        for (int level : needRights) {
          if (User.controllerLevel == level)
          {
            QueueWriter.writeAndFlush(User.cn.socket, message);
            break;
          }
        }
      }
    }
  }
  
  public void addItemEvent(Event task, int ticks)
  {
    addUserEvent(task, ticks);
  }
  
  public void addUserEvent(Event task, int ticks)
  {
    task.Ticks = ticks;
    synchronized (this.eventIdGeneratorUsers)
    {
      this.eventIdGeneratorUsers = Integer.valueOf((this.eventIdGeneratorUsers.intValue() + 1) % 999999);
      this.userEvents.put(this.eventIdGeneratorUsers, task);
      task.eventId = this.eventIdGeneratorUsers;
    }
  }
  
  public void onUserSay(Avatar User, String Message)
  {
    for (PetEntity pet : this.petList.values()) {
      pet.OnUserSay(this, User, Message);
    }
  }
  
  public void addUserToRoom(Connection connection)
  {
    this.VirtualIdCounter = ((short)((this.VirtualIdCounter = (short)(this.VirtualIdCounter + 1)) % 32767));
    
    Avatar avatarEntity = new Avatar(connection, this, this.VirtualIdCounter);
    connection.avatar = avatarEntity;
    
    avatarEntity.SetPos(this.model.doorX, this.model.doorY, this.model.doorZ);
    avatarEntity.SetRot(this.model.DoorOrientation);
    
    avatarEntity.controllerLevel = ControllerLevels.getLevel(connection.playerData, this.roomData, this);
    avatarEntity.entityType = 1;
    if ((avatarEntity.entityType == 1) && (avatarEntity.cn.teleport != null))
    {
      GenericFloorItem Item = (GenericFloorItem)getFloorItem(avatarEntity.cn.teleport.itemId);
      if (Item != null)
      {
        Item.setIntData(2);
        floorItemUpdateNeeded(Item);
        avatarEntity.SetPos(Item.getX(), Item.getY(), Item.getZ());
        avatarEntity.SetRot(Item.getDir());
        addItemEvent(new Teleport_OUT(avatarEntity, Item), 2);
      }
    }
    for (MessengerFriend friend : connection.playerData.messenger.getFriends())
    {
      PlayerData friendPlayer = Clients.getPlayerData(friend.userId);
      if (friendPlayer != null)
      {
        PlayerMessenger messenger = friendPlayer.messenger;
        if (messenger.isOnline) {
          messenger.update(new MessengerFriendUpdate(connection.playerData.userId, 0));
        }
      }
    }
    for (PetEntity roomUser : this.petList.values()) {
      roomUser.OnUserEnterRoom(this, avatarEntity);
    }
    this.userList.put(Integer.valueOf(avatarEntity.id), avatarEntity);
    sendMessage(UsersComposer.compose(avatarEntity));
    
    this.userCount += 1;
    RoomListing.updatePopularRooms(this);
  }
  
  public void deployPet(Pet PetData, int xy, float Z)
  {
    this.VirtualIdCounter = ((short)((this.VirtualIdCounter = (short)(this.VirtualIdCounter + 1)) % 32767));
    PetEntity petEntity = new PetEntity(this, this.VirtualIdCounter);
    
    this.PetCounter += 1;
    int Y;
    int X;
    if (!validTile(xy))
    {
      X = this.model.doorX;
      Y = this.model.doorY;
      Z = this.model.doorZ;
    }
    else
    {
      Y = xy / this.model.widthX;
      X = xy - Y * this.model.widthX;
    }
    petEntity.entityType = 2;
    petEntity.SetPos(X, Y, Z);
    petEntity.SetRot(this.model.DoorOrientation);
    
    petEntity.petData = PetData;
    petEntity.look = (PetData.petType + " " + PetData.base.raceId + " " + PetData.Color);
    petEntity.motto = "";
    PetData.petEntity = petEntity;
    
    entityWalk(petEntity.xy, petEntity, true);
    
    updateUserStatus(petEntity, false);
    
    this.petList.put(Integer.valueOf(PetData.id), petEntity);
    
    sendMessage(UsersComposer.compose(petEntity));
    
    petEntity.OnSelfEnterRoom(this);
  }
  
  public void deployBot(RentalBot botData, int xy, float Z, AvatarLook look, String gender)
  {
    this.VirtualIdCounter = ((short)((this.VirtualIdCounter = (short)(this.VirtualIdCounter + 1)) % 32767));
    RentalBotEntity botEntity = new RentalBotEntity(this, this.VirtualIdCounter);
    
    this.PetCounter += 1;
    int Y;
    int X;
    if (!validTile(xy))
    {
      X = this.model.doorX;
      Y = this.model.doorY;
      Z = this.model.doorZ;
    }
    else
    {
      Y = xy / this.model.widthX;
      X = xy - Y * this.model.widthX;
    }
    botEntity.entityType = 4;
    botEntity.SetPos(X, Y, Z);
    botEntity.SetRot(this.model.DoorOrientation);
    
    botEntity.botData = botData;
    botData.botLook = look;
    botData.gender = gender;
    botData.motto = "I am a bot!";
    botData.botEntity = botEntity;
    
    entityWalk(botEntity.xy, botEntity, true);
    
    updateUserStatus(botEntity, false);
    
    this.rentalBotList.put(Integer.valueOf(botData.id), botEntity);
    
    sendMessage(UsersComposer.compose(botEntity));
    
    botEntity.OnSelfEnterRoom(this);
  }
  
  public FloorItem getFloorItem(int Id)
  {
    return (FloorItem)this.FloorItems.get(Integer.valueOf(Id));
  }
  
  public WallItem getWallItem(int Id)
  {
    return (WallItem)this.WallItems.get(Integer.valueOf(Id));
  }
  
  public PetEntity getRoomPetById(int Id)
  {
    return (PetEntity)this.petList.get(Integer.valueOf(Id));
  }
  
  public RentalBotEntity getRoomBotById(int Id)
  {
    return (RentalBotEntity)this.rentalBotList.get(Integer.valueOf(Id));
  }
  
  public Avatar getRoomUserById(int Id)
  {
    return (Avatar)this.userList.get(Integer.valueOf(Id));
  }
  
  public Avatar getRoomUserByVirtualId(int VirtualId)
  {
    for (Avatar User : this.userList.values()) {
      if (User.virtualId == VirtualId) {
        return User;
      }
    }
    return null;
  }
  
  public float calculateZ(int xy)
  {
    Float newZ = (Float)this.squareAbsoluteHeight.get(Integer.valueOf(xy));
    if (newZ == null) {
      newZ = Float.valueOf(0.0F);
    }
    if (this.squareFlag.have(xy, 2))
    {
      FloorItem top = (FloorItem)this.topFloorItems.get(Integer.valueOf(xy));
      if (top == null) {
        return newZ.floatValue();
      }
      newZ = Float.valueOf(newZ.floatValue() - top.baseItem.Height);
    }
    else if (this.squareFlag.have(xy, 1))
    {
      FloorItem top = (FloorItem)this.topFloorItems.get(Integer.valueOf(xy));
      if (top == null) {
        return newZ.floatValue();
      }
      newZ = Float.valueOf(newZ.floatValue() - top.baseItem.Height);
    }
    return newZ.floatValue();
  }
  
  public void updateUserStatus(LiveEntity User, boolean check)
  {
    Float newZ = (Float)this.squareAbsoluteHeight.get(Integer.valueOf(User.xy));
    if (newZ == null) {
      newZ = Float.valueOf(0.0F);
    }
    if (check)
    {
      if (this.squareFlag.have(User.xy, 2))
      {
        FloorItem top = (FloorItem)this.topFloorItems.get(Integer.valueOf(User.xy));
        if (top != null)
        {
          User.z = top.getZ();
          Direction8 dir = (Direction8)this.MatrixRot.get(Integer.valueOf(User.xy));
          if (dir == null)
          {
            Log.printLog("ERROR: updateUserStatus - MatrixRot=NULL");
          }
          else
          {
            User.RotHead = dir;
            User.RotBody = dir;
          }
          User.setStatus("sit", Float.toString(top.baseItem.Height));
          if ((User instanceof Avatar))
          {
            Avatar avatar = (Avatar)User;
            if (avatar.userSpecialEffect != null) {
              avatar.userSpecialEffect.stopEffect();
            }
          }
          return;
        }
      }
      if (this.squareFlag.have(User.xy, 1))
      {
        FloorItem top = (FloorItem)this.topFloorItems.get(Integer.valueOf(User.xy));
        if (top != null)
        {
          if ((top.getDir() == Direction8.N) || (top.getDir() == Direction8.S)) {
            User.y = top.getY();
          } else {
            User.x = top.getX();
          }
          User.z = top.getZ();
          Direction8 dir = (Direction8)this.MatrixRot.get(Integer.valueOf(User.xy));
          if (dir == null)
          {
            Log.printLog("ERROR: updateUserStatus - MatrixRot=NULL");
          }
          else
          {
            User.RotHead = dir;
            User.RotBody = dir;
          }
          User.setStatus("lay", Float.toString(top.baseItem.Height));
          if ((User instanceof Avatar))
          {
            Avatar avatar = (Avatar)User;
            if (avatar.userSpecialEffect != null) {
              avatar.userSpecialEffect.stopEffect();
            }
          }
          return;
        }
      }
      if (User.HaveStatus("sit")) {
        User.setStatus("", "");
      } else if (User.HaveStatus("lay")) {
        User.setStatus("", "");
      }
    }
    if (newZ.floatValue() != User.z)
    {
      User.z = newZ.floatValue();
      userUpdateNeeded(User);
    }
  }
  
  public void userUpdateNeeded(LiveEntity ent)
  {
    this.UpdateUsers.put(Short.valueOf(ent.virtualId), ent);
  }
  
  public boolean validTile(int xy)
  {
    if ((xy < 0) || (xy >= this.model.widthX * this.model.heightY)) {
      return false;
    }
    return this.model.getSquare(xy) != null;
  }
  
  public void sendMessageCustom(MessageWriter message, MessageWriter alternativeMessage, int... filterIds)
  {
    for (Avatar User : this.userList.values())
    {
      boolean sended = false;
      for (int userId : filterIds) {
        if (User.cn.playerData.userId == userId)
        {
          QueueWriter.writeAndFlush(User.cn.socket, message);
          sended = true;
          break;
        }
      }
      if (!sended) {
        QueueWriter.writeAndFlush(User.cn.socket, alternativeMessage);
      }
    }
  }
  
  private int rollerTest = 0;
  
  public void run()
  {
    try
    {
      if (this.timeOut >= 0) {
        if (this.userCount == 0)
        {
          if (++this.timeOut > 1200)
          {
            this.timeOut = -1;
            RoomManager.setInactive(this.roomData);
            updateMysqlData();
            this.future.cancel(false);
          }
        }
        else if (this.timeOut > 0) {
          this.timeOut = 0;
        }
      }
      if (this.timeOut == -1)
      {
        this.future.cancel(false);
        return;
      }
      if (!ServerProps.STATUS)
      {
        updateMysqlData();
        this.future.cancel(false);
        return;
      }
      long now = System.currentTimeMillis();
      for (Event evt : this.userEvents.values()) {
        parseEvent(evt);
      }
      if (this.rollerTest == 0) {
        RollerEvent.run(this, this.rollers);
      }
      this.rollerTest = ((this.rollerTest + 1) % 4);
      for (PetEntity pet : this.petList.values()) {
        pet.OnTimerTick(this);
      }
      for (RentalBotEntity bot : this.rentalBotList.values()) {
        bot.OnTimerTick(this);
      }
      for (Avatar user : this.userList.values())
      {
        if (user.carryItemID > 0) {
          if (--user.carryTimer < 1) {
            user.CarryItem(0);
          }
        }
        if (++user.idleTime < 600)
        {
          if (user.IsAsleep)
          {
            user.IsAsleep = false;
            
            sendMessage(UserAsleepComposer.compose(user.virtualId, Boolean.valueOf(user.IsAsleep)));
          }
        }
        else if (!user.IsAsleep)
        {
          user.IsAsleep = true;
          sendMessage(UserAsleepComposer.compose(user.virtualId, Boolean.valueOf(user.IsAsleep)));
        }
      }
      FloorItem Item;
      synchronized (this.UpdateFloorItems)
      {
        int size = this.UpdateFloorItems.size();
        if (size > 1)
        {
          sendMessage(ObjectsDataUpdateComposer.compose(this.UpdateFloorItems));
          this.UpdateFloorItems.clear();
        }
        else if (size == 1)
        {
          for (Iterator localIterator2 = this.UpdateFloorItems.iterator(); localIterator2.hasNext();)
          {
            Item = (FloorItem)localIterator2.next();
            sendMessage(ObjectDataUpdateComposer.compose(Item));
          }
          this.UpdateFloorItems.clear();
        }
      }
      synchronized (this.UpdateWallItems)
      {
        if (!this.UpdateWallItems.isEmpty())
        {
          for (WallItem wallIten : this.UpdateWallItems) {
            if (wallIten.getRoomId() == this.roomId) {
              sendMessage(ItemUpdateComposer.compose((GenericWallItem)wallIten));
            }
          }
          this.UpdateWallItems.clear();
        }
      }
      long delay = System.currentTimeMillis() - now;
      if (delay > 10L) {
        Log.printLog("RoomSlow | id=" + this.roomId + " | FloorItems.size()=" + this.FloorItems.size() + " | PetCounter=" + this.PetCounter + " | userCount=" + this.userCount + " | ms=" + delay);
      }
      synchronized (this.UpdateUsers)
      {
        if (!this.UpdateUsers.isEmpty())
        {
          sendMessage(UserUpdateComposer.compose(this.UpdateUsers.values()));
          this.UpdateUsers.clear();
        }
      }
      return;
    }
    catch (Exception ex)
    {
      Log.printException("Room-1", ex);
    }
  }
  
  public boolean canPlacePet(int xy)
  {
    if (!validTile(xy)) {
      return false;
    }
    if (!this.squareFlag.have(xy, 4)) {
      return false;
    }
    if (squareHasUsers(xy)) {
      return false;
    }
    return true;
  }
  
  public boolean canWalk(LiveEntity user, int xy, boolean LastStep)
  {
    if (!validTile(xy)) {
      return false;
    }
    if (user.allowOverride) {
      return true;
    }
    if (!this.squareFlag.have(xy, 4))
    {
      if (!LastStep) {
        return false;
      }
      if (!this.squareFlag.have(xy, 8)) {
        return false;
      }
    }
    if ((!this.roomData.haveFlag(8)) && (squareHasUsers(xy))) {
      return false;
    }
    return true;
  }
  
  private FloorItem updateTopFloorItem(Map<Integer, FloorItem> squareItems)
  {
    float topZ = 0.0F;
    FloorItem top = null;
    for (FloorItem floorItem : squareItems.values()) {
      if ((top == null) || (floorItem.getZ() > topZ))
      {
        topZ = floorItem.getZ();
        top = floorItem;
      }
    }
    return top;
  }
  
  public void generateSquare(int xy, FloorItem item, boolean add, boolean isLoading)
  {
    if (this.model.getSquare(xy) == null) {
      return;
    }
    BaseItem baseIten = item.baseItem;
    
    Map<Integer, FloorItem> squareItems = (Map)this.mapFloorItems.get(Integer.valueOf(xy));
    if ((add) && (squareItems == null))
    {
      squareItems = new ConcurrentHashMap();
      this.mapFloorItems.put(Integer.valueOf(xy), squareItems);
    }
    FloorItem top = (FloorItem)this.topFloorItems.get(Integer.valueOf(xy));
    if (add)
    {
      if ((!isLoading) || (top == null))
      {
        top = item;
        this.topFloorItems.put(Integer.valueOf(xy), top);
      }
      else if ((top.getZ() < item.getZ()) || (
        (top.getZ() == item.getZ()) && (top.baseItem.Height < baseIten.Height)))
      {
        top = item;
        this.topFloorItems.put(Integer.valueOf(xy), top);
      }
      squareItems.put(Integer.valueOf(item.itemId), item);
    }
    else
    {
      squareItems.remove(Integer.valueOf(item.itemId));
      if (squareItems.isEmpty())
      {
        squareItems = null;
        this.mapFloorItems.remove(Integer.valueOf(xy));
      }
      if (top.itemId == item.itemId)
      {
        top = squareItems == null ? null : updateTopFloorItem(squareItems);
        if (top == null) {
          this.topFloorItems.remove(Integer.valueOf(xy));
        } else {
          this.topFloorItems.put(Integer.valueOf(xy), top);
        }
      }
    }
    if (top != null) {
      this.squareAbsoluteHeight.put(Integer.valueOf(xy), Float.valueOf(top.getZ() + top.baseItem.Height));
    } else {
      this.squareAbsoluteHeight.put(Integer.valueOf(xy), Float.valueOf(this.model.getSquare(xy).height));
    }
    if (add)
    {
      if (baseIten.allowSit) {
        this.MatrixRot.put(Integer.valueOf(xy), item.getDir());
      }
      if (baseIten.itemType == BaseItem.ItemType.ROOMGAME_SCORE) {
        if ((baseIten.interactorType == Interactor.InteractorType.banzaiscoreblue) || 
          (baseIten.interactorType == Interactor.InteractorType.footballcounterblue)) {
          this.roomGamesScorersBLUE.add((GenericFloorItem)item);
        } else if ((baseIten.interactorType == Interactor.InteractorType.banzaiscoregreen) || 
          (baseIten.interactorType == Interactor.InteractorType.footballcountergreen)) {
          this.roomGamesScorersGREEN.add((GenericFloorItem)item);
        } else if ((baseIten.interactorType == Interactor.InteractorType.banzaiscorered) || 
          (baseIten.interactorType == Interactor.InteractorType.footballcounterred)) {
          this.roomGamesScorersRED.add((GenericFloorItem)item);
        } else if ((baseIten.interactorType == Interactor.InteractorType.banzaiscoreyellow) || 
          (baseIten.interactorType == Interactor.InteractorType.footballcounteryellow)) {
          this.roomGamesScorersYELLOW.add((GenericFloorItem)item);
        }
      }
    }
    else
    {
      if (baseIten.allowSit) {
        this.MatrixRot.remove(Integer.valueOf(xy));
      }
      if (baseIten.interactorType == Interactor.InteractorType.banzaiscoreblue) {
        this.roomGamesScorersBLUE.remove(item);
      } else if (baseIten.interactorType == Interactor.InteractorType.banzaiscoregreen) {
        this.roomGamesScorersGREEN.remove(item);
      } else if (baseIten.interactorType == Interactor.InteractorType.banzaiscorered) {
        this.roomGamesScorersRED.remove(item);
      } else if (baseIten.interactorType == Interactor.InteractorType.banzaiscoreyellow) {
        this.roomGamesScorersYELLOW.remove(item);
      }
    }
    setupSquareFlags(xy, item, add, isLoading, top);
    
    Map<Short, LiveEntity> users = (Map)this.usersMatrix.get(Integer.valueOf(xy));
    if (users != null) {
      for (LiveEntity roomUser : users.values()) {
        updateUserStatus(roomUser, true);
      }
    }
  }
  
  private void setupSquareFlags(int xy, FloorItem item, boolean add, boolean isLoading, FloorItem topItem)
  {
    BaseItem baseIten = item.baseItem;
    if (add)
    {
      if (baseIten.allowSit)
      {
        this.squareFlag.SetFlag(xy, 4, false);
        this.squareFlag.SetFlag(xy, 8, true);
        if (baseIten.interactorType == Interactor.InteractorType.bed) {
          this.squareFlag.SetFlag(xy, 1, true);
        } else {
          this.squareFlag.SetFlag(xy, 2, true);
        }
      }
      else if ((!isLoading) || (topItem == item))
      {
        this.squareFlag.SetFlag(xy, 4, baseIten.allowWalk);
      }
      if (baseIten.interactorType == Interactor.InteractorType.walkeablechange)
      {
        GenericFloorItem floorItem = (GenericFloorItem)item;
        this.squareFlag.SetFlag(xy, 4, floorItem.getIntData() != 0);
      }
      if (baseIten.interactorType == Interactor.InteractorType.roller)
      {
        this.squareFlag.eventSetFlag(xy, 2, true);
      }
      else if (baseIten.interactorType == Interactor.InteractorType.normslaskates)
      {
        this.squareFlag.eventSetFlag(xy, 4, true);
      }
      else if (baseIten.interactorType == Interactor.InteractorType.banzaifloor)
      {
        this.squareFlag.eventSetFlag(xy, 8, true);
      }
      else if (baseIten.itemType == BaseItem.ItemType.ROOMGAME_GATE)
      {
        this.squareFlag.eventSetFlag(xy, 1, true);
        this.squareFlag.SetFlag(xy, 4, false);
      }
      else if (baseIten.interactorType == Interactor.InteractorType.banzaipuck)
      {
        this.squareFlag.eventSetFlag(xy, 16, true);
      }
      else if (baseIten.interactorType == Interactor.InteractorType.football)
      {
        this.squareFlag.eventSetFlag(xy, 32, true);
      }
      else if (baseIten.itemType == BaseItem.ItemType.FOOTBALL_GOAL)
      {
        this.squareFlag.eventSetFlag(xy, 64, true);
        this.squareFlag.SetFlag(xy, 4, true);
      }
      else if (baseIten.itemType == BaseItem.ItemType.WATER)
      {
        this.squareFlag.eventSetFlag(xy, 128, true);
      }
      else if (baseIten.logic == BaseItem.FurniLogic.FLOORHOLE)
      {
        this.squareFlag.SetFlag(xy, 4, false);
      }
    }
    else
    {
      if (baseIten.allowSit) {
        if (baseIten.interactorType == Interactor.InteractorType.bed) {
          this.squareFlag.SetFlag(xy, 1, false);
        } else {
          this.squareFlag.SetFlag(xy, 2, false);
        }
      }
      if (topItem == null)
      {
        this.squareFlag.SetFlag(xy, 8, false);
        this.squareFlag.SetFlag(xy, 4, true);
      }
      else
      {
        this.squareFlag.SetFlag(xy, 8, topItem.baseItem.allowSit);
        if (topItem.baseItem.interactorType == Interactor.InteractorType.walkeablechange)
        {
          GenericFloorItem floorItem = (GenericFloorItem)topItem;
          this.squareFlag.SetFlag(xy, 4, floorItem.getIntData() != 0);
        }
        else
        {
          this.squareFlag.SetFlag(xy, 4, topItem.baseItem.allowWalk);
        }
      }
      if (baseIten.interactorType == Interactor.InteractorType.roller) {
        this.squareFlag.eventSetFlag(xy, 2, false);
      } else if (baseIten.interactorType == Interactor.InteractorType.normslaskates) {
        this.squareFlag.eventSetFlag(xy, 4, false);
      } else if (baseIten.interactorType == Interactor.InteractorType.banzaifloor) {
        this.squareFlag.eventSetFlag(xy, 8, false);
      } else if (baseIten.itemType == BaseItem.ItemType.ROOMGAME_GATE) {
        this.squareFlag.eventSetFlag(xy, 1, false);
      } else if (baseIten.interactorType == Interactor.InteractorType.banzaipuck) {
        this.squareFlag.eventSetFlag(xy, 16, false);
      } else if (baseIten.interactorType == Interactor.InteractorType.football) {
        this.squareFlag.eventSetFlag(xy, 32, false);
      } else if (baseIten.itemType == BaseItem.ItemType.FOOTBALL_GOAL) {
        this.squareFlag.eventSetFlag(xy, 64, false);
      } else if (baseIten.itemType == BaseItem.ItemType.WATER) {
        this.squareFlag.eventSetFlag(xy, 128, false);
      }
    }
  }
  
  public void floorItemUpdateNeeded(FloorItem florItem)
  {
    synchronized (this.UpdateFloorItems)
    {
      this.UpdateFloorItems.add(florItem);
    }
    florItem.setMysqlState(1);
  }
  
  public void wallItemUpdateNeeded(WallItem wallItem)
  {
    synchronized (this.UpdateWallItems)
    {
      this.UpdateWallItems.add(wallItem);
    }
    wallItem.setMysqlState(1);
  }
  
  private void parseEvent(Event evt)
  {
    if (evt.Ticks-- > 0) {
      return;
    }
    long now = System.currentTimeMillis();
    try
    {
      evt.run(this);
    }
    catch (Exception ex)
    {
      Log.printException("Room-Events", ex);
    }
    long delay = System.currentTimeMillis() - now;
    if (delay > 10L) {
      Log.printLog("RoomEvent slow = " + delay + " | " + evt.toString());
    }
    if (evt.Ticks < 0) {
      this.userEvents.remove(evt.eventId);
    }
  }
  
  public void removeFloorItem(FloorItem floorItem, int pickerId)
  {
    List<RoomFloorItemData.AffectedTile> PointList = floorItem.getAffectedTiles();
    for (RoomFloorItemData.AffectedTile Tile : PointList) {
      generateSquare(Tile.xy, floorItem, false, false);
    }
    if (floorItem.baseItem.interactorType == Interactor.InteractorType.teleport) {
      Teleports.delRoom(floorItem.itemId);
    }
    sendMessage(ObjectRemoveComposer.compose(floorItem, pickerId, 0));
    
    this.FloorItems.remove(Integer.valueOf(floorItem.itemId));
    if ((floorItem instanceof GenericFloorItem)) {
      floorItem.baseItem.interactor.OnPickUp(this, null, (GenericFloorItem)floorItem);
    }
    floorItem.itemPick(floorItem.getXy(), floorItem.getDir());
  }
  
  public void removeWallItem(WallItem wallItem, int pickerId)
  {
    if ((this.MoodlightData != null) && (this.MoodlightData.ItemId == wallItem.itemId)) {
      this.MoodlightData = null;
    }
    sendMessage(ItemRemoveComposer.compose(wallItem, pickerId));
    
    this.WallItems.remove(Integer.valueOf(wallItem.itemId));
    wallItem.cleanRoomData();
  }
  
  public void removePet(PetEntity pet)
  {
    this.petList.remove(Integer.valueOf(pet.petData.id));
    entityWalk(pet.xy, pet, false);
    
    this.PetCounter -= 1;
    pet.petData.petEntity = null;
    sendMessage(UserRemoveComposer.compose(pet.virtualId));
  }
  
  public void removeBot(RentalBotEntity bot)
  {
    this.rentalBotList.remove(Integer.valueOf(bot.botData.id));
    entityWalk(bot.xy, bot, false);
    
    this.PetCounter -= 1;
    bot.botData.botEntity = null;
    sendMessage(UserRemoveComposer.compose(bot.virtualId));
  }
  
  private void setupFloorItemXYZ(FloorItem Item, int newXY, Direction8 newRot, List<RoomFloorItemData.AffectedTile> Points)
  {
    float topZ = 0.0F;
    for (RoomFloorItemData.AffectedTile Tile : Points)
    {
      Float f = (Float)this.squareAbsoluteHeight.get(Integer.valueOf(Tile.xy));
      if ((f != null) && (topZ < f.floatValue())) {
        topZ = f.floatValue();
      }
    }
    Item.setPosition(newXY);
    Item.setPosition(topZ);
    Item.setDir(newRot);
  }
  
  public boolean setFloorItem(Connection User, FloorItem floorItem, int newX, int newY, Direction8 newRot, boolean newItem)
  {
    int newXY = newX + newY * this.model.widthX;
    
    List<RoomFloorItemData.AffectedTile> Points = floorItem.getAffectedTiles(newXY, newRot);
    if (!canPlace(floorItem, Points, newXY, newItem)) {
      return false;
    }
    if (newItem)
    {
      setupFloorItemXYZ(floorItem, newXY, newRot, Points);
      if (floorItem.baseItem.interactorType == Interactor.InteractorType.roller) {
        this.rollers.put(Integer.valueOf(floorItem.itemId), (RollerItem)floorItem);
      }
    }
    else if (!moveObject(floorItem, Points, newXY, newRot))
    {
      return false;
    }
    if (newItem) {
      floorItem.setMysqlState(2);
    } else {
      floorItem.setMysqlState(1);
    }
    floorItem.finishPlace(User, Points, newItem);
    if (newItem) {
      sendMessage(ObjectAddComposer.compose(floorItem));
    } else {
      sendMessage(ObjectUpdateComposer.compose(floorItem));
    }
    return true;
  }
  
  public boolean canPlace(FloorItem floorItem, List<RoomFloorItemData.AffectedTile> Points, int newXY, boolean newItem)
  {
    for (RoomFloorItemData.AffectedTile Tile : Points)
    {
      if (!validTile(Tile.xy)) {
        return false;
      }
      if ((Tile.x == this.model.doorX) && 
        (Tile.y == this.model.doorY)) {
        return false;
      }
      float maxAbsoluteHeight = 49.0F;
      float maxRelativeHeight = this.model.getSquare(Tile.xy).height + 32.0F;
      
      Float f = (Float)this.squareAbsoluteHeight.get(Integer.valueOf(Tile.xy));
      if ((f != null) && (
        (f.floatValue() > maxRelativeHeight) || (f.floatValue() > maxAbsoluteHeight))) {
        return false;
      }
      FloorItem top = (FloorItem)this.topFloorItems.get(Integer.valueOf(Tile.xy));
      if ((top != null) && (top.itemId != floorItem.itemId) && 
        (!top.baseItem.AllowStack)) {
        return false;
      }
      if ((!floorItem.baseItem.allowWalk) && 
        (squareHasUsers(Tile.xy))) {
        return false;
      }
      if (this.squareFlag.eventHave(Tile.xy, 2))
      {
        if ((floorItem.baseItem.xDim != 1) || (floorItem.baseItem.yDim != 1)) {
          return false;
        }
        if ((!newItem) && (floorItem.getXy() != newXY) && 
          (floorItem.baseItem.interactorType == Interactor.InteractorType.roller)) {
          return false;
        }
      }
    }
    return true;
  }
  
  public boolean moveObject(FloorItem floorItem, List<RoomFloorItemData.AffectedTile> Points, int newXY, Direction8 newRot)
  {
    int xy = floorItem.getXy();
    Direction8 dir = floorItem.getDir();
    if ((xy == newXY) && (dir == newRot)) {
      return false;
    }
    List<RoomFloorItemData.AffectedTile> oldTiles = floorItem.getAffectedTiles();
    for (RoomFloorItemData.AffectedTile Tile : oldTiles) {
      if (validTile(Tile.xy)) {
        generateSquare(Tile.xy, floorItem, false, false);
      }
    }
    setupFloorItemXYZ(floorItem, newXY, newRot, Points);
    if ((floorItem instanceof WiredItemBase)) {
      this.wiredManager.moveWired((WiredItemBase)floorItem, floorItem.baseItem.itemType, xy);
    }
    floorItem.itemMoved(xy, dir);
    
    return true;
  }
  
  public void moveObject2(FloorItem floorItem, List<RoomFloorItemData.AffectedTile> Points, int newXY, Direction8 newRot)
  {
    int xy = floorItem.getXy();
    Direction8 dir = floorItem.getDir();
    
    setupFloorItemXYZ(floorItem, newXY, newRot, Points);
    if ((floorItem instanceof WiredItemBase)) {
      this.wiredManager.moveWired((WiredItemBase)floorItem, floorItem.baseItem.itemType, xy);
    }
    floorItem.itemMoved(xy, dir);
  }
  
  public boolean setWallItem(Connection User, GenericWallItem wallItem, boolean newItem)
  {
    if ((wallItem.baseItem.logic == BaseItem.FurniLogic.ROOMDIMMER) && 
      (this.MoodlightData == null))
    {
      this.MoodlightData = new MoodlightData();
      this.MoodlightData.Enabled = true;
      this.MoodlightData.CurrentPreset = 1;
      this.MoodlightData.AddPresent("#000000,255,0");
      this.MoodlightData.AddPresent("#000000,255,0");
      this.MoodlightData.AddPresent("#000000,255,0");
      this.MoodlightData.ItemId = wallItem.itemId;
      wallItem.extraData.setExtraData(this.MoodlightData.GenerateExtraData());
    }
    this.WallItems.put(Integer.valueOf(wallItem.itemId), wallItem);
    
    sendMessage(ItemAddComposer.compose(wallItem));
    if (newItem) {
      wallItem.setMysqlState(2);
    } else {
      wallItem.setMysqlState(1);
    }
    return true;
  }
  
  private void updateRoom()
  {
    String roomTags = "";
    for (String tag : this.roomData.tags)
    {
      if (!roomTags.isEmpty()) {
        roomTags = roomTags.concat(",");
      }
      roomTags = roomTags.concat(tag);
    }
    String iconItems = "";
    int j = 0;
    for (String item : this.roomData.icon.items)
    {
      if (j > 0) {
        iconItems = iconItems.concat("|");
      }
      iconItems = iconItems.concat(item);
      j++;
    }
    try
    {
      Database.exec(
      

























        "UPDATE `rooms` SET `model_name`='" + this.model.modelName + "'," + "`caption`=?," + "`user_id`='" + this.roomData.roomOwner.userId + "'," + "`user_name`=?," + "`description`=?," + "`category`='" + this.roomData.category + "'," + "`state`='" + this.roomData.state + "'," + "`users_max`='" + this.roomData.usersMax + "'," + "`score`='" + this.roomData.rating + "'," + "`tags`=?," + "`icon_bg`='" + this.roomData.icon.backgroundImage + "'," + "`icon_fg`='" + this.roomData.icon.foregroundImage + "'," + "`password`=?," + "`wallpaper`='" + this.roomData.Wallpaper + "'," + "`floor`='" + this.roomData.Floor + "'," + "`landscape`='" + this.roomData.Landscape + "'," + "`allow_pets`='" + (this.roomData.haveFlag(2) ? 1 : 0) + "'," + "`allow_pets_eat`='" + (this.roomData.haveFlag(4) ? 1 : 0) + "'," + "`allow_walkthrough`='" + (this.roomData.haveFlag(8) ? 1 : 0) + "'," + "`allow_hidewall`='" + (this.roomData.haveFlag(16) ? 1 : 0) + "'," + "`wallthickness`='" + this.roomData.wallAnchor + "'," + "`floorthickness`='" + this.roomData.floorAnchor + "'," + "`staff_pickup`='" + (this.roomData.haveFlag(32) ? 1 : 0) + "'," + "`settings_mod`='" + this.roomData.modPermissions.getIntValue() + "'," + "`settings_trd`='" + this.roomData.tradingSettings.getIntValue() + "'," + "`settings_chat`='" + this.roomData.chatSettings.getIntValue() + "'" + " WHERE `id`='" + this.roomId + "';", new Object[] {
        this.roomData.name, 
        this.roomData.roomOwner.userName, 
        this.roomData.description, 
        roomTags, 
        this.roomData.password });
    }
    catch (Exception ex)
    {
      Log.printException("Room-4", ex);
    }
  }
  
  public void updateMysqlData()
  {
    if (!this.roomData.haveFlag(1))
    {
      if ((this.model instanceof CustomGameMap))
      {
        CustomGameMap customModel = (CustomGameMap)this.model;
        if (customModel.mysqlAction == 1) {
          try
          {
            String heightMap = customModel.buildHeightMap();
            Database.exec("INSERT INTO `room_custom_models` (`id`,`base`,`heightmap`) VALUES(?,?,?) ;", new Object[] { customModel.modelName, customModel.baseName, heightMap });
          }
          catch (Exception ex)
          {
            Log.printException("Room-4", ex);
          }
        } else if (customModel.mysqlAction == 2) {
          try
          {
            String heightMap = customModel.buildHeightMap();
            Database.exec("UPDATE `room_custom_models` SET `heightmap`=? WHERE `id` = ?;", new Object[] { heightMap, customModel.modelName });
          }
          catch (Exception ex)
          {
            Log.printException("Room-4", ex);
          }
        }
        customModel.mysqlAction = 0;
      }
      updateRoom();
      for (FloorItem floorItem : this.FloorItems.values()) {
        try
        {
          if (floorItem.baseItem.interactor == Interactor.iterWired)
          {
            if ((floorItem instanceof WiredItemBase))
            {
              WiredItemBase wired = (WiredItemBase)floorItem;
              try
              {
                wired.saveData();
              }
              catch (Exception ex)
              {
                Log.printException("Room-updateMysqlData", ex);
              }
            }
          }
          else if ((floorItem.baseItem.logic == BaseItem.FurniLogic.ROOMDIMMER) && 
            (this.MoodlightData != null) && (this.MoodlightData.ItemId == floorItem.itemId)) {
            this.MoodlightData.mysqlSave();
          }
          floorItem.mysqlSave();
        }
        catch (Exception ex)
        {
          Log.printException("Room", ex);
        }
      }
      for (WallItem wallItem : this.WallItems.values()) {
        try
        {
          wallItem.mysqlSave();
        }
        catch (Exception ex)
        {
          Log.printException("Room", ex);
        }
      }
      for (PetEntity petEntity : this.petList.values())
      {
        Pet pet = petEntity.petData;
        if (pet.needInsert)
        {
          pet.needInsert = false;
          try
          {
            Database.exec("INSERT IGNORE INTO user_pets (id,room_id,x,y,z,type,name,race,color,createstamp,nutrition,expirience,energy,respect,user_id)VALUES(" + pet.id + "," + this.roomId + "," + petEntity.x + "," + petEntity.y + "," + petEntity.z + "," + pet.petType + ",?," + pet.base.raceId + ",?," + pet.TimeCreated + "," + pet.Nutrition + "," + pet.Experience + "," + pet.Energy + "," + pet.Respects + "," + pet.ownerId + ");", new Object[] { pet.name, pet.Color });
          }
          catch (Exception ex)
          {
            Log.printException("Room-updateMysqlData", ex);
          }
        }
        else
        {
          try
          {
            Database.exec("UPDATE user_pets SET `room_id`=" + this.roomId + ",`x`=" + petEntity.x + ",`y`=" + petEntity.y + ",`z`=" + petEntity.z + ",`nutrition`=" + pet.Nutrition + ",`expirience`=" + pet.Experience + ",`energy`=" + pet.Energy + ",`respect`=" + pet.Respects + " WHERE id=" + pet.id + ";", new Object[0]);
          }
          catch (Exception ex)
          {
            Log.printException("Room-updateMysqlData", ex);
          }
        }
      }
      for (RentalBotEntity botEntity : this.rentalBotList.values())
      {
        RentalBot bot = botEntity.botData;
        if (bot.needInsert)
        {
          bot.needInsert = false;
          try
          {
            Database.exec("INSERT IGNORE INTO user_bots (id,room_id,x,y,z,type,name,look,motto,gender,user_id)VALUES(" + bot.id + "," + this.roomId + "," + botEntity.x + "," + botEntity.y + "," + botEntity.z + "," + bot.botType + ",?,?,?,?," + bot.ownerId + ");", new Object[] { bot.name, bot.botLook.toString(), bot.motto, bot.gender });
          }
          catch (Exception ex)
          {
            Log.printException("Room-updateMysqlData", ex);
          }
        }
        else
        {
          try
          {
            Database.exec("UPDATE user_bots SET `room_id`=" + this.roomId + ",`x`=" + botEntity.x + ",`y`=" + botEntity.y + ",`z`=" + botEntity.z + ",`name`=?,`look`=?,`motto`=?,`gender`=? WHERE id=" + bot.id + ";", new Object[] { bot.name, bot.botLook.toString(), bot.motto, bot.gender });
          }
          catch (Exception ex)
          {
            Log.printException("Room-updateMysqlData", ex);
          }
        }
      }
      for (PlayerRight right : this.usersWithRights.values()) {
        if (right.needInsert)
        {
          right.needInsert = false;
          try
          {
            Database.exec("INSERT IGNORE INTO room_rights (room_id,user_id)VALUES(" + this.roomId + "," + right.player.userId + ");", new Object[0]);
          }
          catch (Exception ex)
          {
            Log.printException("Room-updateMysqlData", ex);
          }
        }
      }
    }
    Connection ownerConnection = this.roomData.roomOwner.connection;
    if (ownerConnection != null)
    {
      ownerConnection.saveItems();
      ownerConnection.saveObjects();
    }
  }
  
  public void removeUserFromRoom(Connection client, boolean NotifyClient, boolean NotifyKick)
  {
    removeUserFromRoom(client, client.playerData, NotifyClient, NotifyKick);
  }
  
  public void removeUserFromRoom(Connection cn, PlayerData player, boolean NotifyClient, boolean NotifyKick)
  {
    if (NotifyClient)
    {
      if (NotifyKick) {
        QueueWriter.writeAndFlush(cn.socket, GenericErrorComposer.compose(4008));
      }
      QueueWriter.writeAndFlush(cn.socket, CloseConnectionComposer.compose());
    }
    QueueWriter.writeAndFlush(cn.socket, YouArePlayingGameComposer.compose(false));
    
    Avatar avatar = cn.avatar;
    if (avatar == null) {
      return;
    }
    avatar.evtChat.stop(this);
    avatar.clearMovement();
    entityWalk(avatar.xy, avatar, false);
    sendMessage(UserRemoveComposer.compose(avatar.virtualId));
    if (this.userList.remove(Integer.valueOf(avatar.id)) == null)
    {
      cn.avatar = null;
      return;
    }
    this.userCount -= 1;
    

    Trade trade = (Trade)Trade.tradeMap.get(Integer.valueOf(player.userId));
    if (trade != null)
    {
      trade.clean();
      trade.broadcast(TradingCloseComposer.compose(trade.ownerUser.userId, 0));
    }
    cn.avatar = null;
    for (MessengerFriend friend : player.messenger.getFriends())
    {
      PlayerData friendPlayer = Clients.getPlayerData(friend.userId);
      if (friendPlayer != null)
      {
        PlayerMessenger messenger = friendPlayer.messenger;
        if (messenger.isOnline) {
          messenger.update(new MessengerFriendUpdate(player.userId, 0));
        }
      }
    }
    if (player.userId == this.roomData.roomOwner.userId)
    {
      if (this.roomData.event != null)
      {
        this.roomData.event = null;
        sendMessage(EventComposer.compose());
      }
    }
    else {
      for (PetEntity pet : this.petList.values())
      {
        pet.OnUserLeaveRoom(this, cn);
        if ((pet.entityType == 2) && (pet.petData.ownerId == player.userId))
        {
          cn.inventory.addPet(pet.petData.id, pet.petData);
          QueueWriter.writeAndFlush(cn.socket, AddPetToInventoryComposer.compose(pet.petData));
          removePet(pet);
        }
      }
    }
    RoomListing.updatePopularRooms(this);
  }
  
  public void loadRoom(Connection cn, String Password)
  {
    if (this.usersWithRights == null)
    {
      this.usersWithRights = new ConcurrentHashMap();
      
      DBResult result = new DBResult();
      try
      {
        Database.query(result, "SELECT user_id FROM room_rights WHERE room_id = " + this.roomData.roomId + " LIMIT 1;", new Object[0]);
        while (result.data.next())
        {
          PlayerData player = Clients.getPlayerData(result.data.getInt("user_id"));
          if (player != null) {
            this.usersWithRights.put(Integer.valueOf(player.userId), new PlayerRight(player));
          }
        }
      }
      catch (Exception ex)
      {
        Log.printException("loadRoom", ex);
      }
      result.close();
    }
    if (this.usersBanned == null) {
      this.usersBanned = new ConcurrentHashMap();
    }
    int controllerLevel = ControllerLevels.getLevel(cn.playerData, this.roomData, this);
    if (controllerLevel < 4)
    {
      if (this.userCount >= this.roomData.usersMax)
      {
        QueueWriter.writeAndFlush(cn.socket, FlatAccessDeniedComposer.compose(1, ""));
        QueueWriter.writeAndFlush(cn.socket, CloseConnectionComposer.compose());
        return;
      }
      if ((userIsBanned(cn.playerData.userId)) && 
        (!hasBanExpired(cn.playerData.userId)))
      {
        QueueWriter.writeAndFlush(cn.socket, FlatAccessDeniedComposer.compose(4, ""));
        QueueWriter.writeAndFlush(cn.socket, CloseConnectionComposer.compose());
        return;
      }
      if ((cn.teleport == null) || (cn.teleport.roomId != this.roomData.roomId))
      {
        if (this.roomData.state == 1)
        {
          if (this.userCount == 0)
          {
            QueueWriter.writeAndFlush(cn.socket, DoorBellNoAnswerComposer.compose());
          }
          else
          {
            QueueWriter.writeAndFlush(cn.socket, DoorbellUserComposer.compose(""));
            sendMessage(DoorbellUserComposer.compose(cn.playerData.userName), new int[] { 1, 4, 5 });
          }
          return;
        }
        if ((this.roomData.state == 2) && 
          (!Password.equals(this.roomData.password)))
        {
          QueueWriter.writeAndFlush(cn.socket, GenericErrorComposer.compose(-100002));
          QueueWriter.writeAndFlush(cn.socket, CloseConnectionComposer.compose());
          return;
        }
      }
    }
    startLoadingRoom(cn);
  }
  
  public void startLoadingRoom(Connection cn)
  {
    QueueWriter.writeAndFlush(cn.socket, OpenConnectionComposer.compose());
    

    QueueWriter.writeAndFlush(cn.socket, RoomReadyComposer.compose(this.model.modelName, this.roomData.roomId));
    if (!this.roomData.Floor.equals("0.0")) {
      QueueWriter.writeAndFlush(cn.socket, RoomPropertyComposer.compose("floor", this.roomData.Floor));
    }
    if (!this.roomData.Wallpaper.equals("0.0")) {
      QueueWriter.writeAndFlush(cn.socket, RoomPropertyComposer.compose("wallpaper", this.roomData.Wallpaper));
    }
    QueueWriter.writeAndFlush(cn.socket, RoomPropertyComposer.compose("landscape", this.roomData.Landscape));
    
    int controllerLevel = ControllerLevels.getLevel(cn.playerData, this.roomData, this);
    if (controllerLevel != 0)
    {
      QueueWriter.writeAndFlush(cn.socket, YouAreControllerComposer.compose(controllerLevel));
      if (cn.playerData.userId == this.roomData.roomOwner.userId) {
        QueueWriter.writeAndFlush(cn.socket, YouAreOwnerComposer.compose());
      }
    }
    else
    {
      QueueWriter.writeAndFlush(cn.socket, YouAreNotControllerComposer.compose());
    }
    QueueWriter.writeAndFlush(cn.socket, RoomRatingComposer.compose(this.roomData.rating, (!cn.avatarData.ratedRooms.contains(Integer.valueOf(this.roomData.roomId))) && (cn.playerData.userId != this.roomData.roomOwner.userId)));
    
    cn.avatarData.LoadingRoom = this.roomData.roomId;
  }
}


