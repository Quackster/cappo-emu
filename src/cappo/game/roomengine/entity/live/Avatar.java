package cappo.game.roomengine.entity.live;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.FactorialServerHandler;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.catalog.Catalog;
import cappo.game.collections.BaseItem;
import cappo.game.collections.Utils;
import cappo.game.games.snowwar.Direction8;
import cappo.game.landing.LandingNews;
import cappo.game.moderation.UserMuted;
import cappo.game.navigator.officialrooms.OfficialRooms;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.player.RightsManager;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.polls.PollManager;
import cappo.game.roomeffects.UserEffect;
import cappo.game.roomeffects.special.UserSpecialEffect;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.chat.UserRoomMuted;
import cappo.game.roomengine.chat.wf.WordFilter;
import cappo.game.roomengine.chat.wf.WordFilterAction;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.wired.trigger.WiredTriggerBase;
import cappo.game.roomengine.entity.item.wall.WallItem;
import cappo.game.roomengine.roomevents.UserChat;
import cappo.game.roomengine.roomevents.User_WALK;
import cappo.game.roomengine.wired.WiredManager;
import cappo.game.roomgames.RoomGamePlayer;
import cappo.game.sound.trax.Trax;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.inventory.furni.FurniListAddOrUpdateComposer;
import cappo.protocol.messages.composers.inventory.furni.FurniListUpdateComposer;
import cappo.protocol.messages.composers.inventory.purse.CreditBalanceComposer;
import cappo.protocol.messages.composers.notifications.HabboActivityPointNotificationComposer;
import cappo.protocol.messages.composers.notifications.HabboBroadcastCustomComposer;
import cappo.protocol.messages.composers.room.action.CarryObjectComposer;
import cappo.protocol.messages.composers.room.action.UserDanceComposer;
import cappo.protocol.messages.composers.room.action.UserEffectComposer;
import cappo.protocol.messages.composers.room.chat.ChatComposer;
import cappo.protocol.messages.composers.room.chat.FloodControlComposer;
import cappo.protocol.messages.composers.room.chat.ShoutComposer;
import cappo.protocol.messages.composers.room.chat.WhisperComposer;
import cappo.protocol.messages.composers.room.engine.UserChangeComposer;
import cappo.protocol.messages.composers.serializers.SerializeSay;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Avatar
  extends LiveEntity
{
  public int carryItemID;
  public int carryTimer;
  public Connection cn;
  public int DanceId;
  public int floodCount = 0;
  public int id;
  public int idleTime;
  public boolean IsAsleep;
  public boolean IsBuyEffect;
  public boolean IsDancing;
  public long lastSay = 0L;
  public int controllerLevel;
  public RoomGamePlayer roomGamePlayer;
  public UserEffect userEffect;
  public UserSpecialEffect userSpecialEffect;
  public UserRoomMuted userRoomMuted;
  public List<FloorItem> onFloorItems;
  public UserChat evtChat;
  
  public Avatar(Connection User, RoomTask room, short virtualId)
  {
    super(room, virtualId);
    
    this.id = User.getPlayerData().userId;
    this.cn = User;
    this.evtChat = new UserChat();
    this.idleTime = 0;
  }
  
  public void CarryItem(int ItemId)
  {
    this.carryItemID = ItemId;
    this.carryTimer = (ItemId > 0 ? 240 : 0);
    this.room.sendMessage(CarryObjectComposer.compose(this.virtualId, ItemId));
  }
  
  public boolean equals(Object arg0)
  {
    if ((arg0 instanceof Avatar)) {
      return ((Avatar)arg0).id == this.id;
    }
    return false;
  }
  
  public void SetRot(Direction8 Rotation, boolean HeadOnly)
  {
    if (this.evtWalk.isWalking) {
      return;
    }
    super.SetRot(Rotation, HeadOnly);
  }
  
  public boolean canChat()
  {
    if (this.cn.userMuted != null)
    {
      if (this.cn.userMuted.isMuted()) {
        return false;
      }
      this.cn.userMuted = null;
    }
    if (this.userRoomMuted != null)
    {
      if (this.userRoomMuted.isMuted()) {
        return false;
      }
      this.userRoomMuted = null;
    }
    if ((this.room.roomData.muteAllOn) && 
      (this.controllerLevel < 4)) {
      return false;
    }
    long currentTime = System.currentTimeMillis();
    if (this.floodCount == -1)
    {
      if (this.lastSay > currentTime) {
        return false;
      }
      this.floodCount = 0;
    }
    else
    {
      if (this.controllerLevel < 5) {
        this.lastSay += 2000L;
      } else {
        this.lastSay += 500L;
      }
      if (this.lastSay > currentTime)
      {
        if (++this.floodCount > 4)
        {
          int Time = 15;
          
          QueueWriter.writeAndFlush(this.cn.socket, FloodControlComposer.compose(15));
          
          this.floodCount = -1;
          this.lastSay = (currentTime + 15000L);
          return false;
        }
      }
      else {
        this.floodCount = 0;
      }
    }
    this.lastSay = currentTime;
    
    return true;
  }
  
  public void say(String message, int styleId, int sayId, boolean isShout)
  {
    if (sayId > 20) {
      sayId = 0;
    }
    if (!canChat()) {
      return;
    }
    message = message.trim();
    if ((message.isEmpty()) || (message.length() > 100)) {
      return;
    }
    try
    {
      if ((message.charAt(0) == ':') && (parseCmd(message))) {
        return;
      }
    }
    catch (Exception localException)
    {
      if (WiredTriggerBase.launchTriggers(this.room.wiredManager.triggersUserSays, this.cn, message))
      {
        QueueWriter.writeAndFlush(this.cn.socket, WhisperComposer.compose(this.virtualId, message, 0, 0, new ArrayList(), 0));
        return;
      }
      this.room.onUserSay(this, message);
      


      WordFilterAction action = WordFilter.getAction(message);
      if ((action != null) && (action.run(this.cn))) {
        return;
      }
      List<String> Urls = new ArrayList();
      message = ParseMessage(message, Urls);
      if ((styleId == 1) || (styleId == 2)) {
        styleId = 0;
      }
      if ((styleId == 23) && (!this.cn.getPlayerData().allowModTools())) {
        styleId = 0;
      }
      if (this.cn.getPlayerData().useChatBot()) {
        styleId = 2;
      }
      if (isShout) {
        this.evtChat.talk(this.room, ShoutComposer.compose(this.virtualId, message, GetSpeechEmotion(message), styleId, Urls, sayId));
      } else {
        this.evtChat.talk(this.room, ChatComposer.compose(this.virtualId, message, GetSpeechEmotion(message), styleId, Urls, sayId));
      }
    }
  }
  
  public static int GetSpeechEmotion(String Message)
  {
    Message = Message.toLowerCase();
    if ((Message.contains(":)")) || (Message.contains(":d")) || (Message.contains("=]")) || (Message.contains("=d")) || (Message.contains(":>"))) {
      return 1;
    }
    if ((Message.contains(">:(")) || (Message.contains(":@"))) {
      return 2;
    }
    if (Message.contains(":o")) {
      return 3;
    }
    if ((Message.contains(":(")) || (Message.contains("=[")) || (Message.contains(":'(")) || (Message.contains("='["))) {
      return 4;
    }
    return 0;
  }
  
  public boolean parseCmd(String cmd)
  {
    String[] parsed = cmd.split(" ");
    MessageWriter clientMessage;
    if (parsed[0].equals(":test"))
    {
      int header = Integer.parseInt(parsed[1]);
      List<String> urls = new ArrayList();
      clientMessage = new MessageWriter();
      Composer.initPacket(header, clientMessage);
      SerializeSay.parse(clientMessage, this.virtualId, "test:" + header, 0, 0, urls, 0);
      Composer.endPacket(clientMessage);
      this.evtChat.talk(this.room, clientMessage);
      return true;
    }
    if (parsed[0].equals(":commands"))
    {
      String response = "Commands:\n";
      response = response + ":pickall\n";
      response = response + ":copy\n";
      response = response + ":moonwalk\n";
      response = response + ":toggletrade\n";
      response = response + ":togglediagonal\n";
      response = response + ":clearhand\n";
      response = response + ":effect\n";
      PlayerData playerData = this.cn.getPlayerData();
      if (playerData.staffLevel > 1)
      {
        response = response + "\nStaff Commands:\n";
        if (playerData.staffLevel > 2)
        {
          response = response + ":gethere\n";
          response = response + ":follow\n";
        }
        if (playerData.allowRoomAlert()) {
          response = response + ":ra\n";
        }
        if (playerData.allowHotelAlert())
        {
          response = response + ":ha\n";
          response = response + ":hal\n";
          response = response + ":popout\n";
          response = response + ":bubble\n";
          response = response + ":rdance\n";
        }
        if (playerData.allowHotelImageAlert()) {
          response = response + ":hia\n";
        }
        if (playerData.allowDataReload())
        {
          response = response + ":loadcata\n";
          response = response + ":loadwf\n";
          response = response + ":loadlanding\n";
          response = response + ":loadofficials\n";
          response = response + ":loadrights\n";
          response = response + ":reloaduser\n";
          response = response + ":setmax\n";
        }
        if (playerData.allowGiveBadge())
        {
          response = response + ":givebadge\n";
          response = response + ":massbadge\n";
          response = response + ":rbadge\n";
        }
        if (playerData.allowGiveMoney())
        {
          response = response + ":givediamonds\n";
          response = response + ":massdiamonds\n";
          response = response + ":rdiamonds\n";
          
          response = response + ":giveducks\n";
          response = response + ":massducks\n";
          response = response + ":rducks\n";
          
          response = response + ":givecredits\n";
          response = response + ":masscredits\n";
          response = response + ":rcredits\n";
          
          response = response + ":delbadge\n";
          response = response + ":delbadges\n";
        }
      }
      Utils.AlertFromHotel(this.cn.socket, response);
      return true;
    }
    if (parsed[0].equals(":pickall"))
    {
      PlayerData playerData = this.cn.getPlayerData();
      for (WallItem wallItem : this.room.WallItems.values()) {
        if (wallItem.owner.userId == playerData.userId)
        {
          this.room.removeWallItem(wallItem, playerData.userId);
          if (wallItem.baseItem.itemCategory != 6)
          {
            this.cn.inventoryAddWallItem(wallItem);
            wallItem.setMysqlState(2);
            
            QueueWriter.writeAndFlush(this.cn.socket, FurniListAddOrUpdateComposer.compose(wallItem));
          }
        }
      }
      for (FloorItem floorItem : this.room.FloorItems.values()) {
        if (floorItem.owner.userId == playerData.userId)
        {
          this.room.removeFloorItem(floorItem, playerData.userId);
          this.cn.inventoryAddFloorItem(floorItem);
          floorItem.setMysqlState(2);
          
          QueueWriter.writeAndFlush(this.cn.socket, FurniListAddOrUpdateComposer.compose(floorItem));
        }
      }
      return true;
    }
    PlayerData playerData;
    if (parsed[0].equals(":copy"))
    {
      if (parsed.length < 2) {
        return false;
      }
      PlayerData player = Clients.getPlayerDataLoaded(parsed[1]);
      if (player == null) {
        return false;
      }
      playerData = this.cn.getPlayerData();
      
      playerData.avatarLook = player.avatarLook;
      playerData.sex = player.sex;
      
      QueueWriter.writeAndFlush(this.cn.socket, UserChangeComposer.compose(-1, playerData.avatarLook.toString(), playerData.sex, playerData.motto, playerData.AchievementsScore));
      this.room.sendMessage(UserChangeComposer.compose(this.virtualId, playerData.avatarLook.toString(), playerData.sex, playerData.motto, playerData.AchievementsScore));
      return true;
    }
    if (parsed[0].equals(":moonwalk"))
    {
      this.cn.xorFlag(32);
      return true;
    }
    if (parsed[0].equals(":toggletrade"))
    {
      this.cn.xorFlag(8);
      return true;
    }
    if (parsed[0].equals(":togglediagonal"))
    {
      this.cn.xorFlag(64);
      return true;
    }
    if (parsed[0].equals(":effect"))
    {
      this.room.sendMessage(UserEffectComposer.compose(this.virtualId, Integer.parseInt(parsed[1])));
      return true;
    }
    if (parsed[0].equals(":clearhand"))
    {
      for (RoomData data : this.cn.ownRooms.values())
      {
        RoomTask room = data.room;
        if (room != null) {
          room.updateMysqlData();
        }
      }
      this.cn.saveItems();
      this.cn.saveObjects();
      
      this.cn.inventory.clearFurnis();
      QueueWriter.writeAndFlush(this.cn.socket, FurniListUpdateComposer.compose());
      try
      {
        Database.exec(
        


          "DELETE da,db,dc FROM furnis AS da LEFT JOIN furnis_roomdata AS db ON db.id=da.id LEFT JOIN furnis_floorextra AS dc ON dc.id=da.id WHERE da.userid=" + this.cn.getPlayerData().userId + " AND da.roomid=0;", new Object[0]);
      }
      catch (Exception ex)
      {
        Log.printException("Item-Delete", ex);
      }
      return true;
    }
    playerData = this.cn.getPlayerData();
    if (playerData.staffLevel > 1)
    {
      Avatar avatar;
      if (playerData.staffLevel > 2)
      {
        if (parsed[0].equals(":follow"))
        {
          PlayerData client = Clients.getPlayerDataLoaded(parsed[1]);
          if ((client == null) || (client.connection == null)) {
            return false;
          }
          avatar = client.connection.avatar;
          if (avatar == null) {
            return false;
          }
          this.cn.loadRoom(avatar.room.roomId, avatar.room.roomData.password);
          return true;
        }
        if (parsed[0].equals(":gethere"))
        {
          PlayerData client = Clients.getPlayerDataLoaded(parsed[1]);
          if ((client == null) || (client.connection == null)) {
            return false;
          }
          client.connection.loadRoom(this.room.roomId, this.room.roomData.password);
          return true;
        }
      }
      if ((playerData.allowRoomAlert()) && 
        (parsed[0].equals(":ra")))
      {
        for (Avatar user : this.room.userList.values()) {
          Utils.AlertFromHotel(user.cn.socket, cmd.substring(4) + "\n\n- " + playerData.userName);
        }
        return true;
      }
      if (playerData.allowHotelAlert())
      {
        if (parsed[0].equals(":ha"))
        {
          Utils.AlertFromHotel(FactorialServerHandler.channels, cmd.substring(4) + "\n\n- " + playerData.userName);
          return true;
        }
        if (parsed[0].equals(":popout"))
        {
          Map<String, String> popout = new HashMap();
          popout.put("display", "POP_UP");
          FactorialServerHandler.channels.writeAndFlush(
            HabboBroadcastCustomComposer.compose(parsed[1], popout));
          
          return true;
        }
        if (parsed[0].equals(":bubble"))
        {
          Map<String, String> bubble = new HashMap();
          bubble.put("display", "BUBBLE");
          FactorialServerHandler.channels.writeAndFlush(
            HabboBroadcastCustomComposer.compose(parsed[1], bubble));
          
          return true;
        }
        if (parsed[0].equals(":hal"))
        {
          FactorialServerHandler.channels.writeAndFlush(
            HabboBroadcastCustomComposer.compose(parsed[1], null));
          
          return true;
        }
        if (parsed[0].equals(":rdance"))
        {
          int DanceId = Integer.parseInt(parsed[1]);
          if (DanceId != 1) {
            if (DanceId < 0) {
              DanceId = 0;
            } else if (DanceId > 8) {
              DanceId = 0;
            }
          }
          for (Avatar user : this.room.userList.values())
          {
            if ((DanceId > 0) && (user.carryItemID > 0)) {
              user.CarryItem(0);
            }
            user.DanceId = DanceId;
            this.room.sendMessage(UserDanceComposer.compose(user.virtualId, DanceId));
          }
          return true;
        }
      }
      if ((playerData.allowHotelImageAlert()) && 
        (parsed[0].equals(":hia")))
      {
        Utils.broadcastImage(FactorialServerHandler.channels, parsed[1]);
        return true;
      }
      if (parsed[0].equals(":capoloadcata"))
      {
        Catalog.block();
        DBResult result = new DBResult();
        try
        {
          Trax.Init(result);
          BaseItem.Init(result);
          Catalog.Init(result);
        }
        catch (Exception ex)
        {
          Log.printException("Avatar", ex);
        }
        result.close();
        Catalog.unblock();
        return true;
      }
      if (playerData.allowDataReload())
      {
        if (parsed[0].equals(":loadcata"))
        {
          Catalog.block();
          DBResult result = new DBResult();
          try
          {
            Trax.Init(result);
            BaseItem.Init(result);
            Catalog.Init(result);
          }
          catch (Exception ex)
          {
            Log.printException("Avatar", ex);
          }
          result.close();
          Catalog.unblock();
          return true;
        }
        if (parsed[0].equals(":loadwf"))
        {
          DBResult result = new DBResult();
          try
          {
            WordFilter.init(result);
          }
          catch (Exception ex)
          {
            Log.printException("Avatar", ex);
          }
          result.close();
          return true;
        }
        if (parsed[0].equals(":loadlanding"))
        {
          DBResult result = new DBResult();
          try
          {
            LandingNews.Init(result);
          }
          catch (Exception ex)
          {
            Log.printException("Avatar", ex);
          }
          result.close();
          return true;
        }
        if (parsed[0].equals(":loadofficials"))
        {
          DBResult result = new DBResult();
          try
          {
            OfficialRooms.init(result);
          }
          catch (Exception ex)
          {
            Log.printException("Officials", ex);
          }
          result.close();
          return true;
        }
        if (parsed[0].equals(":loadrights"))
        {
          DBResult result = new DBResult();
          try
          {
            RightsManager.load(result);
          }
          catch (Exception ex)
          {
            Log.printException("loadRights", ex);
          }
          result.close();
          return true;
        }
        if (parsed[0].equals(":loadpolls"))
        {
          DBResult result = new DBResult();
          try
          {
            PollManager.load(result);
          }
          catch (Exception ex)
          {
            Log.printException("loadPolls", ex);
          }
          result.close();
          return true;
        }
        if (parsed[0].equals(":reloaduser"))
        {
          PlayerData client = Clients.getPlayerDataLoaded(parsed[1]);
          if (client == null)
          {
            Utils.AlertFromHotel(this.cn.socket, "User is not loaded, so not need reload!");
            return true;
          }
          if (client.connection != null)
          {
            Utils.AlertFromHotel(this.cn.socket, "User is connected, you cant reload user!");
            return true;
          }
          Clients.deleteID(client.userId);
          return true;
        }
        if (parsed[0].equals(":setmax"))
        {
          RoomData roomData = this.room.roomData;
          if (roomData == null) {
            return false;
          }
          int maxUsers = Integer.parseInt(parsed[1]);
          if ((maxUsers % 5 == 0) && (
            (maxUsers > 300) || (maxUsers < 10))) {
            return false;
          }
          roomData.updateMaxUsers(maxUsers);
          
          return true;
        }
      }
      if (playerData.allowGiveBadge())
      {
        if (parsed[0].equals(":givebadge"))
        {
          PlayerData Client = Clients.getPlayerDataLoaded(parsed[1]);
          if ((Client == null) || (Client.connection == null)) {
            return false;
          }
          String badgeid = parsed[2];
          if (badgeid.contains("?")) {
            return false;
          }
          Client.connection.giveBadge(badgeid);
          return true;
        }
        Object ch;
        if (parsed[0].equals(":massbadge"))
        {
          String badgeid = parsed[1];
          if (badgeid.contains("?")) {
            return false;
          }
          Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
          while (itr.hasNext())
          {
            ch = (Channel)itr.next();
            Connection con = (Connection)((Channel)ch).attr(FactorialServerHandler.CONNECTION).get();
            con.giveBadge(badgeid);
          }
          return true;
        }
        if (parsed[0].equals(":rbadge"))
        {
          String badgeid = parsed[1];
          for (ch = this.room.userList.values().iterator(); ((Iterator)ch).hasNext();)
          {
            Avatar user = (Avatar)((Iterator)ch).next();
            if (badgeid.contains("?")) {
              return false;
            }
            user.cn.giveBadge(badgeid);
          }
          return true;
        }
      }
      if (playerData.allowGiveMoney())
      {
        if (parsed[0].equals(":givediamonds"))
        {
          PlayerData Client = Clients.getPlayerDataLoaded(parsed[1]);
          if ((Client == null) || (Client.connection == null)) {
            return false;
          }
          Connection plrConnection = Client.connection;
          
          int give = Integer.parseInt(parsed[2]);
          QueueWriter.writeAndFlush(Client.connection.socket, HabboActivityPointNotificationComposer.compose(plrConnection.diamondAmmount += give, give, 3));
          Utils.AlertFromHotel(Client.connection.socket, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[9] + "\n\n- " + playerData.userName);
          return true;
        }
        Object ch;
        if (parsed[0].equals(":massdiamonds"))
        {
          int give = Integer.parseInt(parsed[1]);
          
          Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
          while (itr.hasNext())
          {
            ch = (Channel)itr.next();
            Connection con = (Connection)((Channel)ch).attr(FactorialServerHandler.CONNECTION).get();
            if (con.avatarData != null) {
              QueueWriter.writeAndFlush((Channel)ch, HabboActivityPointNotificationComposer.compose(con.diamondAmmount += give, give, 3));
            }
          }
          Utils.AlertFromHotel(FactorialServerHandler.channels, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[9] + "\n\n- " + playerData.userName);
          return true;
        }
        if (parsed[0].equals(":rdiamonds"))
        {
          int give = Integer.parseInt(parsed[1]);
          for (ch = this.room.userList.values().iterator(); ((Iterator)ch).hasNext();)
          {
            Avatar user = (Avatar)((Iterator)ch).next();
            Connection con = user.cn;
            if (con != null)
            {
              QueueWriter.writeAndFlush(con.socket, HabboActivityPointNotificationComposer.compose(con.diamondAmmount += give, give, 3));
              Utils.AlertFromHotel(con.socket, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[9] + "\n\n- " + playerData.userName);
            }
          }
          return true;
        }
        if (parsed[0].equals(":giveducks"))
        {
          PlayerData Client = Clients.getPlayerDataLoaded(parsed[1]);
          if ((Client == null) || (Client.connection == null)) {
            return false;
          }
          Connection plrConnection = Client.connection;
          
          int give = Integer.parseInt(parsed[2]);
          QueueWriter.writeAndFlush(Client.connection.socket, HabboActivityPointNotificationComposer.compose(plrConnection.pixelAmmount += give, give, 0));
          Utils.AlertFromHotel(Client.connection.socket, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[2] + "\n\n- " + playerData.userName);
          return true;
        }
        if (parsed[0].equals(":massducks"))
        {
          int give = Integer.parseInt(parsed[1]);
          
          Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
          while (itr.hasNext())
          {
            ch = (Channel)itr.next();
            Connection con = (Connection)((Channel)ch).attr(FactorialServerHandler.CONNECTION).get();
            if (con.avatarData != null) {
              QueueWriter.writeAndFlush((Channel)ch, HabboActivityPointNotificationComposer.compose(con.pixelAmmount += give, give, 0));
            }
          }
          Utils.AlertFromHotel(FactorialServerHandler.channels, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[2] + "\n\n- " + playerData.userName);
          return true;
        }
        if (parsed[0].equals(":rducks"))
        {
          int give = Integer.parseInt(parsed[1]);
          for (ch = this.room.userList.values().iterator(); ((Iterator)ch).hasNext();)
          {
            Avatar user = (Avatar)((Iterator)ch).next();
            Connection con = user.cn;
            if (con != null)
            {
              QueueWriter.writeAndFlush(con.socket, HabboActivityPointNotificationComposer.compose(con.pixelAmmount += give, give, 0));
              Utils.AlertFromHotel(con.socket, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[2] + "\n\n- " + playerData.userName);
            }
          }
          return true;
        }
        if (parsed[0].equals(":givecredits"))
        {
          PlayerData Client = Clients.getPlayerDataLoaded(parsed[1]);
          if ((Client == null) || (Client.connection == null)) {
            return false;
          }
          Connection plrConnection = Client.connection;
          
          int give = Integer.parseInt(parsed[2]);
          QueueWriter.writeAndFlush(plrConnection.socket, CreditBalanceComposer.compose(plrConnection.credits += give));
          Utils.AlertFromHotel(plrConnection.socket, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[1] + "\n\n- " + playerData.userName);
          return true;
        }
        if (parsed[0].equals(":masscredits"))
        {
          int give = Integer.parseInt(parsed[1]);
          
          Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
          while (itr.hasNext())
          {
            ch = (Channel)itr.next();
            Connection con = (Connection)((Channel)ch).attr(FactorialServerHandler.CONNECTION).get();
            if (con.avatarData != null) {
              QueueWriter.writeAndFlush((Channel)ch, CreditBalanceComposer.compose(con.credits += give));
            }
          }
          Utils.AlertFromHotel(FactorialServerHandler.channels, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[1] + "\n\n- " + playerData.userName);
          return true;
        }
        if (parsed[0].equals(":rcredits"))
        {
          int give = Integer.parseInt(parsed[1]);
          for (ch = this.room.userList.values().iterator(); ((Iterator)ch).hasNext();)
          {
            Avatar user = (Avatar)((Iterator)ch).next();
            Connection con = user.cn;
            if (con != null)
            {
              QueueWriter.writeAndFlush(con.socket, CreditBalanceComposer.compose(con.credits += give));
              Utils.AlertFromHotel(con.socket, cappo.game.utils.lang.LangTexts.texts[0] + give + cappo.game.utils.lang.LangTexts.texts[1] + "\n\n- " + playerData.userName);
            }
          }
          return true;
        }
        if (parsed[0].equals(":delbadge"))
        {
          PlayerData Client = Clients.getPlayerDataLoaded(parsed[1]);
          if ((Client == null) || (Client.connection == null)) {
            return false;
          }
          Client.connection.delBadge(parsed[2]);
          try
          {
            Database.exec("DELETE FROM user_badges WHERE user_id = " + Client.userId + " AND  badge_id = ?;", new Object[] { parsed[2] });
          }
          catch (Exception ex)
          {
            Log.printException("Item-Delete", ex);
          }
          return true;
        }
        if (parsed[0].equals(":delbadges"))
        {
          String badge = parsed[1];
          
          Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
          while (itr.hasNext())
          {
            ch = (Channel)itr.next();
            Connection con = (Connection)((Channel)ch).attr(FactorialServerHandler.CONNECTION).get();
            con.delBadge(badge);
          }
          try
          {
            Database.exec("DELETE FROM user_badges WHERE badge_id = ?;", new Object[] { badge });
          }
          catch (Exception ex)
          {
            Log.printException("Item-Delete", ex);
          }
          return true;
        }
      }
    }
    return false;
  }
  
  private static final char[] HttP = { 'h', 't', 't', 'p' };
  private static final char[] Separator = { ':', '/', '/' };
  
  public static String ParseMessage(String Message, List<String> Urls)
  {
    String NewMsg = "";
    int len = Message.length();
    int UrlCount = 0;
    int pos = 0;
    int a = 0;
    for (; pos < len; pos++) {
      if (HttP[a] == Message.charAt(pos))
      {
        a++;
        if (a == 4)
        {
          a = 0;
          if (len > pos + 4) {
            if (Separator[a] == Message.charAt(++pos))
            {
              if ((Separator[(++a)] == Message.charAt(++pos)) && 
                (Separator[(++a)] == Message.charAt(++pos)))
              {
                int init = pos - 6;
                do
                {
                  pos++;
                } while ((pos < len) && 
                  (' ' != Message.charAt(pos)));
                NewMsg = NewMsg.concat(Message.substring(0, init)).concat("{").concat(Integer.toString(UrlCount)).concat("}");
                UrlCount++;
                Urls.add(Message.substring(init, pos));
                Message = Message.substring(pos);
                len -= pos;
                pos = 0;
              }
            }
            else if ((Separator[a] == Message.charAt(++pos)) && 
              (Separator[(++a)] == Message.charAt(++pos)) && 
              (Separator[(++a)] == Message.charAt(++pos)))
            {
              int init = pos - 7;
              do
              {
                pos++;
              } while ((pos < len) && 
                (' ' != Message.charAt(pos)));
              NewMsg = NewMsg.concat(Message.substring(0, init)).concat("{").concat(Integer.toString(UrlCount)).concat("}");
              UrlCount++;
              Urls.add(Message.substring(init, pos));
              Message = Message.substring(pos);
              len -= pos;
              pos = 0;
            }
          }
          a = 0;
        }
      }
      else
      {
        a = 0;
      }
    }
    if (len > 0) {
      NewMsg = NewMsg + Message.substring(0, pos);
    }
    return NewMsg;
  }
}


