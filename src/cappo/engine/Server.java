package cappo.engine;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.CappoServer;
import cappo.engine.network.FactorialServerHandler;
import cappo.engine.threadpools.WorkerTasks;
import cappo.game.catalog.Catalog;
import cappo.game.collections.BaseItem;
import cappo.game.collections.Utils;
import cappo.game.games.GamesManager;
import cappo.game.landing.LandingNews;
import cappo.game.navigator.NavigatorCategories;
import cappo.game.navigator.officialrooms.OfficialRooms;
import cappo.game.player.RightsManager;
import cappo.game.polls.PollManager;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.chat.wf.WordFilter;
import cappo.game.sound.trax.Trax;
import cappo.protocol.messages.OpCodesManager;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Properties;

public final class Server
{
  public static SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
  public static SimpleDateFormat date2 = new SimpleDateFormat("dd.MMM.yyyy HH:mm");
  private static Integer ItemIdCount;
  private static Integer RoomIdCount;
  private static Integer badgeIdCount;
  private static ShutdownInstance rmiShutdown;
  private static Registry rmiRegistry;
  public static boolean blockFF;
  public static boolean blockMysql;
  public static boolean blockTickets;
  public static String serverId;
  public static String ssoSecretKey;
  public static String mysqlDB;
  public static String fastfoodIP;
  public static String fastfoodPORT;
  public static Integer automaticGiveCredits;
  public static Integer automaticGiveDuckets;
  private static Integer refItemIdCount = Integer.valueOf(1);
  
  public static int generateRefItemId()
  {
    synchronized (refItemIdCount)
    {
      refItemIdCount = Integer.valueOf(refItemIdCount.intValue() + 1);
      return refItemIdCount.intValue();
    }
  }
  
  public static int generateItemId()
  {
    synchronized (ItemIdCount)
    {
      ItemIdCount = Integer.valueOf(ItemIdCount.intValue() + 1);
      return ItemIdCount.intValue();
    }
  }
  
  public static int generateRoomId()
  {
    synchronized (RoomIdCount)
    {
      RoomIdCount = Integer.valueOf(RoomIdCount.intValue() + 1);
      return RoomIdCount.intValue();
    }
  }
  
  public static int generateBadgeId()
  {
    synchronized (badgeIdCount)
    {
      badgeIdCount = Integer.valueOf(badgeIdCount.intValue() + 1);
      return badgeIdCount.intValue();
    }
  }
  
  public static void main(String[] args)
    throws Exception
  {
    if (args.length < 1) {
      return;
    }
    try
    {
      serverId = args[0];
      if (args.length > 1)
      {
        System.setSecurityManager(new RMISecurityManager());
        try
        {
          RemoteCommand shutdown = (RemoteCommand)Naming.lookup("rmi://127.0.0.1/" + args[1] + "_" + serverId);
          System.out.println(shutdown.exec());
        }
        catch (Exception ex)
        {
          rmiRegistry = LocateRegistry.getRegistry();
          rmiRegistry.unbind("shutdown_" + serverId);
        }
        return;
      }
      rmiRegistry = LocateRegistry.getRegistry();
      rmiShutdown = new ShutdownInstance();
      rmiRegistry.rebind("shutdown_" + serverId, rmiShutdown);
      
      Properties props = new Properties();
      props.load(Server.class.getClassLoader().getResourceAsStream("Cappo.properties"));
      cappo.game.utils.lang.LangTexts.texts[0] = props.getProperty("lang.1", "�Has recibido ");
      cappo.game.utils.lang.LangTexts.texts[1] = props.getProperty("lang.2", " Cr�ditos!");
      cappo.game.utils.lang.LangTexts.texts[2] = props.getProperty("lang.3", " Duckets!");
      cappo.game.utils.lang.LangTexts.texts[3] = props.getProperty("lang.4", "Lo sentimos, este modelo de sala no esta disponible.");
      cappo.game.utils.lang.LangTexts.texts[4] = props.getProperty("lang.5", "�El nombre de la sala es muy corto!");
      cappo.game.utils.lang.LangTexts.texts[5] = props.getProperty("lang.6", "Bienvenido a Lavvos Beta!!");
      cappo.game.utils.lang.LangTexts.texts[6] = props.getProperty("lang.7", "Inventory is Full!");
      cappo.game.utils.lang.LangTexts.texts[7] = props.getProperty("lang.8", "�Eh!, levanta el pie. Tu tambi�n puedes hacer que Lavvos sea un lugar m�s c�modo para tod@s. Por Favor, respeta la Manera Lavvos.");
      cappo.game.utils.lang.LangTexts.texts[8] = props.getProperty("lang.9", "�Por qu� publicas otros hoteles? �No has visto nuestra �ltimas novedades? �El Fastfood te ha parecido poco? Te invito a que descubras un sin fin de cosas nuevas que OTROS no tienen. �Lavvos tu mejor opcion!.");
      cappo.game.utils.lang.LangTexts.texts[9] = props.getProperty("lang.10", " Diamonds!");
      
      ServerProps.STATUS = true;
      
      fastfoodIP = props.getProperty("ff.ip", "ff.lavvos.pl");
      fastfoodPORT = props.getProperty("ff.port", "30002");
      blockFF = props.getProperty("ff.block", "false").equals("true");
      blockMysql = props.getProperty("mysql.block", "false").equals("true");
      blockTickets = props.getProperty("game.tickets.block", "false").equals("true");
      
      ssoSecretKey = props.getProperty("sso.secretkey", "log#in#key");
      mysqlDB = props.getProperty("mysql.db");
      
      automaticGiveCredits = Integer.valueOf(Integer.parseInt(props.getProperty("game.give.credits", "0")));
      automaticGiveDuckets = Integer.valueOf(Integer.parseInt(props.getProperty("game.give.ducks", "0")));
      
      int port = Integer.parseInt(props.getProperty("port.game"));
      
      Log.Init(props.getProperty("log.infile", "false").equals("false"), date2.format(Utils.GetDateNow()));
      Log.printLog("Starting Cappo 2.0.0");
      
      OpCodesManager.init();
      
      Database.Init(props.getProperty("mysql.host"), props.getProperty("mysql.port"), mysqlDB, props.getProperty("mysql.user"), props.getProperty("mysql.pass"));
      
      DBResult result = new DBResult();
      PollManager.load(result);
      RightsManager.load(result);
      LandingNews.Init(result);
      NavigatorCategories.Init(result);
      OfficialRooms.init(result);
      RoomManager.Init(result);
      Trax.Init(result);
      BaseItem.Init(result);
      Catalog.Init(result);
      WordFilter.init(result);
      
      Database.query(result, "SELECT item_id FROM items ORDER BY item_id DESC LIMIT 1;", new Object[0]);
      ItemIdCount = Integer.valueOf(result.data.next() ? result.data.getInt("item_id") : 0);
      
      Database.query(result, "SELECT id FROM furnis ORDER BY id DESC LIMIT 1;", new Object[0]);
      ItemIdCount = Integer.valueOf(result.data.next() ?
        (result.data.getInt("id") > ItemIdCount.intValue() ? result.data.getInt("id") : ItemIdCount.intValue()) :
        ItemIdCount.intValue());
      
      Database.query(result, "SELECT id FROM `rooms` ORDER BY `id` DESC LIMIT 1;", new Object[0]);
      RoomIdCount = Integer.valueOf(result.data.next() ? result.data.getInt("id") : 0);
      Database.query(result, "SELECT id FROM `user_badges` ORDER BY `id` DESC LIMIT 1;", new Object[0]);
      badgeIdCount = Integer.valueOf(result.data.next() ? result.data.getInt("id") : 0);
      

      result.close();
      


      int serverType = 1;
      try
      {
        String type = props.getProperty("game.type");
        if (type != null) {
          serverType = Integer.parseInt(type);
        }
      }
      catch (Exception localException1) {}
      WorkerTasks.initWorkers(serverType);
      

      Thread t = new Thread(new ServerTasks());
      t.setPriority(1);
      t.start();
      
      GamesManager.initManager();
      

      new CappoServer(port).run();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      

      rmiRegistry.unbind("shutdown_" + serverId);
      return;
    }
    while (ServerProps.STATUS) {
      try
      {
        Thread.sleep(1000L);
      }
      catch (Exception localException2) {}
    }
    Utils.AlertFromHotel(FactorialServerHandler.channels, "Disconnecting: Server Shutting Down");
    
    Log.printLog("Server: closing..");
    try
    {
      rmiRegistry.unbind("shutdown_" + serverId);
      UnicastRemoteObject.unexportObject(rmiShutdown, false);
    }
    catch (Exception ex)
    {
      Log.printException("closing", ex);
    }
    Log.printLog("Flushing Sockets");
    
    CappoServer.shutdown();
  }
}


