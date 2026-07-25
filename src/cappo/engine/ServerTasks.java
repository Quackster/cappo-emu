package cappo.engine;

import cappo.engine.logging.Log;
import cappo.engine.network.FactorialServerHandler;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.tasks.OnlineCounter;
import cappo.engine.tasks.OnlineCounterGrapth;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.OpCodesManager;
import cappo.protocol.messages.composers.handshake.ConnectionPingComposer;
import cappo.protocol.messages.composers.inventory.purse.CreditBalanceComposer;
import cappo.protocol.messages.composers.notifications.HabboActivityPointNotificationComposer;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerTasks
  extends Thread
{
  private static int counter1 = 0;
  private static int counter2 = 0;
  private static int counter3 = 0;
  public static final MessageWriter PingMessage = ConnectionPingComposer.compose();
  
  private static void cleanClients()
  {
    try
    {
      long notused = Utils.getTimestamp() - 1800L;
      
      Object[] keys = Clients.GetClients().keySet().toArray();
      
      int loops = keys.length / 10 + 1;
      int a = 0;
      do
      {
        try
        {
          Thread.sleep(20L);
        }
        catch (Exception localException1) {}
        int end = a + 10;
        if (end > keys.length) {
          end = keys.length;
        }
        for (; a < end; a++)
        {
          PlayerData current = Clients.getPlayerData(((Integer)keys[a]).intValue());
          if (current != null)
          {
            Connection cn = current.connection;
            if (cn == null)
            {
              if (current.LastUsedThis < notused) {
                Clients.deleteID(current.userId);
              }
            }
            else {
              try
              {
                if (cn.haveFlag(2))
                {
                  QueueWriter.writeAndFlush(cn.socket, PingMessage);
                  cn.setFlag(2, false);
                }
              }
              catch (Exception ex)
              {
                Log.printException("", ex);
              }
            }
          }
        }
        loops--;
      } while (loops >= 0);
    }
    catch (Exception ex)
    {
      Log.printException("ServerTasks", ex);
    }
  }
  
  private static void cleanRooms()
  {
    try
    {
      long notused = Utils.getTimestamp() - 3600L;
      
      Map<Integer, RoomData> rooms = RoomManager.GetRooms();
      Object[] keys = rooms.keySet().toArray();
      
      int loops = keys.length / 10 + 1;
      int a = 0;
      do
      {
        try
        {
          Thread.sleep(20L);
        }
        catch (Exception localException1) {}
        int end = a + 10;
        if (end > keys.length) {
          end = keys.length;
        }
        for (; a < end; a++)
        {
          RoomData current = (RoomData)rooms.get(Integer.valueOf(((Integer)keys[a]).intValue()));
          if (current != null) {
            if (current.room == null) {
              if (current.lastUsedThis <= notused) {
                if ((current.roomOwner == null) || (current.roomOwner.connection == null)) {
                  RoomManager.unloadRoom(current.roomId);
                }
              }
            }
          }
        }
        loops--;
      } while (loops >= 0);
    }
    catch (Exception ex)
    {
      Log.printException("ServerTasks", ex);
    }
  }
  
  private static void giveCredits()
  {
    try
    {
      int credits = Server.automaticGiveCredits.intValue();
      int ducks = Server.automaticGiveDuckets.intValue();
      if ((credits > 0) || (ducks > 0)) {
        if ((credits > 0) && (ducks > 0))
        {
          Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
          while (itr.hasNext())
          {
            Channel ch = (Channel)itr.next();
            Connection con = (Connection)ch.attr(FactorialServerHandler.CONNECTION).get();
            if (con.avatarData != null)
            {
              QueueWriter.writeAndFlush(ch, CreditBalanceComposer.compose(con.credits += credits));
              QueueWriter.writeAndFlush(ch, HabboActivityPointNotificationComposer.compose(con.pixelAmmount += ducks, ducks, 0));
            }
          }
          Utils.AlertFromHotel(FactorialServerHandler.channels, cappo.game.utils.lang.LangTexts.texts[0] + credits + cappo.game.utils.lang.LangTexts.texts[1] + "\n" + cappo.game.utils.lang.LangTexts.texts[0] + ducks + cappo.game.utils.lang.LangTexts.texts[2] + "\n\n");
        }
        else if (credits > 0)
        {
          Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
          while (itr.hasNext())
          {
            Channel ch = (Channel)itr.next();
            Connection con = (Connection)ch.attr(FactorialServerHandler.CONNECTION).get();
            if (con.avatarData != null) {
              QueueWriter.writeAndFlush(ch, CreditBalanceComposer.compose(con.credits += credits));
            }
          }
          Utils.AlertFromHotel(FactorialServerHandler.channels, cappo.game.utils.lang.LangTexts.texts[0] + credits + cappo.game.utils.lang.LangTexts.texts[1] + "\n\n");
        }
        else if (ducks > 0)
        {
          Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
          while (itr.hasNext())
          {
            Channel ch = (Channel)itr.next();
            Connection con = (Connection)ch.attr(FactorialServerHandler.CONNECTION).get();
            if (con.avatarData != null) {
              QueueWriter.writeAndFlush(ch, HabboActivityPointNotificationComposer.compose(con.pixelAmmount += ducks, ducks, 0));
            }
          }
          Utils.AlertFromHotel(FactorialServerHandler.channels, cappo.game.utils.lang.LangTexts.texts[0] + ducks + cappo.game.utils.lang.LangTexts.texts[2] + "\n\n");
        }
      }
    }
    catch (Exception ex)
    {
      Log.printException("ServerTasks", ex);
    }
  }
  
  public void run()
  {
    ScheduledThreadPoolExecutor statsWorker = new ScheduledThreadPoolExecutor(1);
    statsWorker.scheduleAtFixedRate(new OnlineCounter(), 60L, 5L, TimeUnit.SECONDS);
    statsWorker.scheduleAtFixedRate(new OnlineCounterGrapth(), 60L, 60L, TimeUnit.SECONDS);
    do
    {
      try
      {
        Thread.sleep(5000L);
        
        OpCodesManager.checkComposerOverrides();
        OpCodesManager.checkParserOverrides();
        if (counter1++ > 555)
        {
          counter1 = 0;
          
          new Thread()
          {
            public void run() {}
          }.start();
        }
        if (counter2++ > 200)
        {
          counter2 = 0;
          
          new Thread()
          {
            public void run() {}
          }.start();
        }
        if (counter3++ > 200)
        {
          counter3 = 0;
          
          new Thread()
          {
            public void run() {}
          }.start();
        }
      }
      catch (Exception ex)
      {
        Log.printException("ServerTasks", ex);
      }
    } while (ServerProps.STATUS);
    statsWorker.shutdown();
  }
}


