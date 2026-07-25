package cappo.engine.player;
import cappo.game.roomengine.itemInteractor.InteractorTeleport;

import cappo.engine.Server;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.Crypto;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.bots.RentalBot;
import cappo.game.collections.AvatarEffect;
import cappo.game.collections.Badge;
import cappo.game.collections.BaseItem;
import cappo.game.collections.FavRoom;
import cappo.game.collections.UnseenItems;
import cappo.game.collections.Utils;
import cappo.game.collections.Wardrobe;
import cappo.game.moderation.StaffManager;
import cappo.game.moderation.UserMuted;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.player.PlayerModerator;
import cappo.game.player.SnowWarPlayerData;
import cappo.game.player.data.AvatarData;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.game.roomeffects.UserEffect;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.roomengine.entity.item.floor.wired.WiredItemBase;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.itemInteractor.InteractorTeleport.TeleportAttach;
import cappo.protocol.messages.composers.inventory.avatareffect.EffectAddedComposer;
import cappo.protocol.messages.composers.inventory.avatareffect.EffectEnabledComposer;
import cappo.protocol.messages.composers.inventory.avatareffect.EffectStopedComposer;
import cappo.protocol.messages.composers.inventory.badges.BadgesComposer;
import cappo.protocol.messages.composers.inventory.furni.FurniListRemoveComposer;
import cappo.protocol.messages.composers.notifications.HabboActivityPointNotificationComposer;
import cappo.protocol.messages.composers.notifications.UnseenItemsComposer;
import io.netty.channel.Channel;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Connection
{
  private BigInteger prime;
  private BigInteger privateKey;
  private BigInteger publicKey;
  public Crypto RC4Decode;
  public long nextPixelsUpdate;
  public int credits;
  public int diamondAmmount;
  public int pixelAmmount;
  public int vipPoins;
  public int homeRoom;
  public int respects;
  public int dailyPetRespectPoints;
  public int dailyRespectPoints;
  public int MaxRooms;
  public Avatar avatar;
  public AvatarData avatarData;
  public InteractorTeleport.TeleportAttach teleport;
  public PlayerInventory inventory;
  public SnowWarPlayerData snowWarPlayerData;
  public UserMuted userMuted;
  public List<AvatarEffect> avatarEffects;
  public Map<String, Badge> badges;
  public Map<Integer, Badge> badgesSelected;
  public Map<Integer, FavRoom> favoriteRooms;
  public Map<Short, Wardrobe> Wardrobes;
  public List<Integer> ignoreUsers;
  public Map<Integer, RoomData> ownRooms;
  public Channel socket;
  public MessageReader currentPacket;
  public PlayerData playerData;
  private int flags;
  
  public Connection()
  {
    this.avatarData = new AvatarData();
    this.inventory = new PlayerInventory(this);
    
    this.avatarEffects = new ArrayList();
    this.badges = new ConcurrentHashMap();
    this.badgesSelected = new ConcurrentHashMap();
    this.favoriteRooms = new ConcurrentHashMap();
    this.Wardrobes = new ConcurrentHashMap();
    this.ignoreUsers = new ArrayList();
    this.ownRooms = new ConcurrentHashMap();
  }
  
  public PlayerData getPlayerData()
  {
    return this.playerData;
  }
  
  public void setPlayerData(PlayerData player)
  {
    this.playerData = player;
    this.playerData.connection = this;
    this.snowWarPlayerData = new SnowWarPlayerData(player);
  }
  
  public boolean haveFlag(int bit)
  {
    return (this.flags & bit) == bit;
  }
  
  public final void setFlag(int flag, boolean add)
  {
    if (add) {
      this.flags |= flag;
    } else {
      this.flags &= (flag ^ 0xFFFFFFFF);
    }
  }
  
  public final void xorFlag(int flag)
  {
    this.flags ^= flag;
  }
  
  public void inventoryAddFloorItem(FloorItem item)
  {
    this.inventory.addObject(item.itemId, item);
    if (item.baseItem.itemCategory == 8) {
      this.inventory.addSong(item.itemId, (SongItem)item);
    }
  }
  
  public void inventoryAddWallItem(WallItem item)
  {
    this.inventory.addItem(item.itemId, item);
  }
  
  public void addEffect(int EffectId, int Duration)
  {
    AvatarEffect effect = new AvatarEffect(EffectId, Duration, false, Utils.getTimestamp());
    this.avatarEffects.add(effect);
    QueueWriter.writeAndFlush(this.socket, EffectAddedComposer.compose(effect));
  }
  
  public void applyEffect(short EffectId)
  {
    if (this.avatar == null) {
      return;
    }
    if (!HasEffect(EffectId, true)) {
      return;
    }
    this.avatar.IsBuyEffect = (EffectId > 0);
    if (this.avatar.IsBuyEffect) {
      this.avatar.userEffect = new UserEffect(this.avatar, EffectId);
    } else if (this.avatar.userEffect != null) {
      this.avatar.userEffect.stopEffect();
    }
  }
  
  public void saveUserData()
  {
    try
    {
      if (this.playerData != null) {
        Database.exec(
        











          "UPDATE `users` LEFT JOIN `user_info` ON (user_info.user_id=users.id) SET users.username=?,users.motto=?,users.look=?,users.respects=" + this.respects + "," + "users.home_room=" + this.homeRoom + "," + "users.credits=" + this.credits + "," + "users.activity_points=" + this.pixelAmmount + "," + "users.crystals=" + this.diamondAmmount + "," + "users.vip_points=" + this.vipPoins + "," + "users.gender='" + (this.playerData.sex == 1 ? "M" : "F") + "'," + "users.block_trade='" + (haveFlag(8) ? 1 : 0) + "'," + "users.newbie_status=0" + " WHERE users.id=" + this.playerData.userId + ";", new Object[] { this.playerData.userName, this.playerData.motto, this.playerData.avatarLook.toString() });
      }
    }
    catch (Exception ex)
    {
      Log.printException("saveItems", ex);
    }
  }
  
  public void saveFavRooms()
  {
    try
    {
      if (this.favoriteRooms != null) {
        for (FavRoom fav : this.favoriteRooms.values()) {
          if (fav.needInsert)
          {
            fav.needInsert = false;
            
            Database.exec("INSERT IGNORE INTO user_favorites (room_id,user_id)VALUES(" + fav.room.roomId + "," + this.playerData.userId + ");", new Object[0]);
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("saveFavRooms", ex);
    }
  }
  
  public void saveWardRobes()
  {
    try
    {
      if (this.Wardrobes != null) {
        for (Wardrobe wrb : this.Wardrobes.values()) {
          if (wrb.needInsert)
          {
            wrb.needInsert = false;
            
            Database.exec("INSERT INTO user_wardrobe (slot_id,look,gender,user_id)VALUES(" + wrb.slotId + ",'" + wrb.look + "','" + (wrb.gender == 1 ? "M" : "F") + "'," + this.playerData.userId + ") on DUPLICATE KEY UPDATE `look`='" + wrb.look + "',`gender`='" + (wrb.gender == 1 ? "M" : "F") + "';", new Object[0]);
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("saveWardRobes", ex);
    }
  }
  
  public void saveBadges()
  {
    try
    {
      if (this.badges != null) {
        for (Badge badge : this.badges.values()) {
          if (badge.needInsert)
          {
            badge.needInsert = false;
            
            Database.exec("INSERT INTO user_badges (id,badge_id,badge_slot,user_id)VALUES(" + badge.badgeId + ",?," + badge.badgeSlot + "," + this.playerData.userId + ") on DUPLICATE KEY UPDATE `badge_slot`=" + badge.badgeSlot + ";", new Object[] { badge.badgeCode });
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("saveBadges", ex);
    }
  }
  
  public void savePets()
  {
    try
    {
      if (this.inventory.petsReady()) {
        for (Pet pet : this.inventory.getPets()) {
          if (pet.needInsert)
          {
            pet.needInsert = false;
            
            Database.exec("INSERT INTO user_pets (id,type,name,race,color,createstamp,nutrition,expirience,energy,respect,user_id,room_id)VALUES(" + pet.id + "," + pet.petType + ",?," + pet.base.raceId + ",?," + pet.TimeCreated + "," + pet.Nutrition + "," + pet.Experience + "," + pet.Energy + "," + pet.Respects + "," + this.playerData.userId + ",0);", new Object[] { pet.name, pet.Color });
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("savePets", ex);
    }
  }
  
  public void saveBots()
  {
    try
    {
      if (this.inventory.botsReady()) {
        for (RentalBot bot : this.inventory.getBots()) {
          if (bot.needInsert)
          {
            bot.needInsert = false;
            
            Database.exec("INSERT INTO user_bots (id,type,name,look,gender,motto,user_id,room_id)VALUES(" + bot.id + "," + bot.botType + ",?,?,?,?," + this.playerData.userId + ",0);", new Object[] { bot.name, bot.botLook.toString(), bot.gender, bot.motto });
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("savePets", ex);
    }
  }
  
  public void saveItems()
  {
    try
    {
      if (this.inventory.itemsReady()) {
        for (WallItem wallItem : this.inventory.getItems()) {
          try
          {
            wallItem.mysqlSave();
          }
          catch (Exception ex)
          {
            Log.printException("saveItems", ex);
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("savePets", ex);
    }
  }
  
  public void saveObjects()
  {
    try
    {
      if (this.inventory.objectsReady()) {
        for (FloorItem floorItem : this.inventory.getObjects()) {
          try
          {
            if (floorItem.baseItem.interactor == Interactor.iterWired) {
              if ((floorItem instanceof WiredItemBase))
              {
                WiredItemBase wired = (WiredItemBase)floorItem;
                try
                {
                  wired.deleteData();
                }
                catch (Exception ex)
                {
                  Log.printException("channelDisconnected", ex);
                }
              }
            }
            floorItem.mysqlSave();
          }
          catch (Exception ex)
          {
            Log.printException("saveObjects", ex);
          }
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("savePets", ex);
    }
  }
  
  public void channelDisconnected()
  {
    if (haveFlag(128)) {
      return;
    }
    setFlag(128, true);
    if (this.playerData == null) {
      return;
    }
    this.playerData.connection = null;
    if (this.playerData.userId != 0)
    {
      if ((this.playerData instanceof PlayerModerator)) {
        StaffManager.removeStaff(this.playerData.userId);
      }
      Clients.setOnline(false);
      try
      {
        if (this.avatar != null)
        {
          RoomTask room = this.avatar.room;
          if (room != null) {
            room.removeUserFromRoom(this, this.playerData, false, false);
          }
        }
      }
      catch (Exception ex)
      {
        Log.printException("Disconnect", ex);
      }
      try
      {
        if (this.snowWarPlayerData != null) {
          this.snowWarPlayerData.userLeft();
        }
      }
      catch (Exception ex)
      {
        Log.printException("Disconnect", ex);
      }
      saveBots();
      saveItems();
      saveObjects();
      savePets();
      saveBadges();
      saveWardRobes();
      saveFavRooms();
      saveUserData();
      try
      {
        this.playerData.messenger.save();
        this.inventory.clean();
      }
      catch (Exception ex)
      {
        Log.printException("Disconnect", ex);
      }
    }
  }
  
  public void EffectsCheckExpired()
  {
    for (AvatarEffect Effect : this.avatarEffects) {
      if (Effect.Activated)
      {
        long diff = Utils.getTimestamp() - Effect.ActivateTimestamp;
        if (diff > Effect.TotalDuration) {
          StopEffect(Effect);
        }
      }
    }
  }
  
  public void EnableEffect(int EffectId)
  {
    AvatarEffect Effect = GetEffect(EffectId, false);
    if (Effect == null) {
      return;
    }
    if (Effect.Activated) {
      return;
    }
    Effect.Activated = true;
    Effect.ActivateTimestamp = Utils.getTimestamp();
    
    QueueWriter.writeAndFlush(this.socket, EffectEnabledComposer.compose(Effect.effectType, Effect.TotalDuration));
  }
  
  public String generateRandomHexString(int len)
  {
    String result = "";
    Random rnd = new Random();
    for (int i = 0; i < len; i++)
    {
      int rand = 1 + (int)(rnd.nextDouble() * 254.0D);
      result = result + Integer.toString(rand, 16);
    }
    return result;
  }
  
  public String generateSharedKey(BigInteger ClientKey)
  {
    return ClientKey.modPow(this.privateKey, this.prime).toString(16).toUpperCase();
  }
  
  private AvatarEffect GetEffect(int EffectId, boolean IfEnabledOnly)
  {
    if (IfEnabledOnly) {
      for (AvatarEffect Effect : this.avatarEffects) {
        if ((Effect.effectType == EffectId) && (Effect.Activated) && (Utils.getTimestamp() - Effect.ActivateTimestamp < Effect.TotalDuration)) {
          return Effect;
        }
      }
    } else {
      for (AvatarEffect Effect : this.avatarEffects) {
        if (Effect.effectType == EffectId) {
          return Effect;
        }
      }
    }
    return null;
  }
  
  public void inventoryRemoveItem(int itemId, boolean isWall)
  {
    QueueWriter.writeAndFlush(this.socket, FurniListRemoveComposer.compose(itemId));
    if (isWall)
    {
      this.inventory.removeItem(itemId);
    }
    else
    {
      FloorItem Item = this.inventory.removeObject(itemId);
      if (Item.baseItem.itemCategory == 8) {
        this.inventory.removeSong(itemId);
      }
    }
  }
  
  public String getPublicKey()
  {
    String p = this.publicKey.toString(10);
    

    this.prime = null;
    this.privateKey = null;
    this.publicKey = null;
    
    return p;
  }
  
  public void giveBadge(String code)
  {
    if (this.badges.containsKey(code)) {
      return;
    }
    Badge badge = new Badge(Server.generateBadgeId(), code, 0);
    badge.needInsert = true;
    
    this.badges.put(code, badge);
    QueueWriter.writeAndFlush(this.socket, BadgesComposer.compose(this.badges.values(), this.badgesSelected.values()));
    
    this.avatarData.UnseenItems.AddItem(4, badge.badgeId);
    QueueWriter.writeAndFlush(this.socket, UnseenItemsComposer.compose(this.avatarData.UnseenItems));
  }
  
  public void delBadge(String code)
  {
    Badge badge = (Badge)this.badges.remove(code);
    if (badge == null) {
      return;
    }
    QueueWriter.writeAndFlush(this.socket, BadgesComposer.compose(this.badges.values(), this.badgesSelected.values()));
  }
  
  public void GivePixels(int i)
  {
    QueueWriter.writeAndFlush(this.socket, HabboActivityPointNotificationComposer.compose(this.pixelAmmount += i, i, 0));
    this.nextPixelsUpdate = (Utils.getTimestamp() + 600L);
  }
  
  private boolean HasEffect(int EffectId, boolean IfEnabledOnly)
  {
    if (EffectId == -1) {
      return true;
    }
    if (IfEnabledOnly) {
      for (AvatarEffect Effect : this.avatarEffects) {
        if ((Effect.effectType == EffectId) && (Effect.Activated) && (Utils.getTimestamp() - Effect.ActivateTimestamp < Effect.TotalDuration)) {
          return true;
        }
      }
    } else {
      for (AvatarEffect Effect : this.avatarEffects) {
        if ((Effect.effectType == EffectId) && (Utils.getTimestamp() - Effect.ActivateTimestamp < Effect.TotalDuration)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public byte[] HextoBytes(String hexString)
  {
    if (hexString.length() % 2 != 0) {
      hexString = "0" + hexString;
    }
    byte[] bytes = new byte[hexString.length() / 2];
    int j = 0;
    for (int i = 0; i < bytes.length; i++)
    {
      bytes[i] = ((byte)Integer.parseInt(hexString.substring(j, j + 2), 16));
      j += 2;
    }
    return bytes;
  }
  
  public void InitDH(BigInteger p, BigInteger generator, String bigrand)
  {
    this.prime = p;
    this.privateKey = new BigInteger(bigrand, 16);
    this.publicKey = generator.modPow(this.privateKey, this.prime);
  }
  
  public void loadRoom(int RoomId, String Password)
  {
    if (this.avatar != null)
    {
      RoomTask oldRoom = this.avatar.room;
      if (oldRoom != null) {
        oldRoom.removeUserFromRoom(this, false, false);
      }
    }
    RoomData roomData = RoomManager.getRoom(RoomId);
    if (roomData == null)
    {
      try
      {
        roomData = RoomManager.loadRoom(RoomId);
      }
      catch (Exception ex)
      {
        Log.printException("Connection-3", ex);
      }
      if (roomData == null) {
        return;
      }
    }
    RoomTask room = roomData.room;
    if (room == null)
    {
      room = roomData.room = new RoomTask(roomData);
      if (room.model == null) {
        return;
      }
      room.init();
      RoomTask.addTask(room, 0, 500);
    }
    room.loadRoom(this, Password);
  }
  
  public boolean PixelsNeedsUpdate()
  {
    if (Utils.getTimestamp() > this.nextPixelsUpdate) {
      return true;
    }
    return false;
  }
  
  private void StopEffect(AvatarEffect Effect)
  {
    this.avatarEffects.remove(Effect);
    
    QueueWriter.writeAndFlush(this.socket, EffectStopedComposer.compose(Effect.effectType));
    if (this.avatar == null) {
      return;
    }
    if (this.avatar.userEffect != null) {
      this.avatar.userEffect.stopEffect();
    }
  }
}
