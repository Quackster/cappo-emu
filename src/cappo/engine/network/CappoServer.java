package cappo.engine.network;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.threadpools.WorkerTasks;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CappoServer
{
  public static Channel serverChannel;
  private static NioEventLoopGroup bossGroup;
  private static NioEventLoopGroup workerGroup;
  private static int port;
  
  public CappoServer(int p)
  {
    port = p;
  }
  
  public void run()
    throws Exception
  {
    Log.printLog("Opening Game Port : " + port);
    if (port == 666) {
      ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
    } else {
      ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
    }
    bossGroup = new NioEventLoopGroup();
    if (WorkerTasks.serverType > 2) {
      workerGroup = new NioEventLoopGroup(8);
    } else if (WorkerTasks.serverType > 1) {
      workerGroup = new NioEventLoopGroup(3);
    } else {
      workerGroup = new NioEventLoopGroup(2);
    }
    final FactorialServerHandler handler = new FactorialServerHandler();
    
    ServerBootstrap b = new ServerBootstrap();
    

    ((ServerBootstrap)b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, Integer.valueOf(200)))
      .childHandler(new ChannelInitializer<SocketChannel>()
      {
        public void initChannel(SocketChannel ch)
          throws Exception
        {
          ch.pipeline().addLast(new ChannelHandler[] { new ReadTimeoutHandler(100), 
            new HabboPacketDecoder(), new HabboPacketEncoder(), handler });
        }
      }).childOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
    

    serverChannel = b.bind(port).sync().channel();
    
    Log.printLog("Done.");
  }
  
  public static void close(Channel ch)
  {
    if (ch == null) {
      return;
    }
    ch.close();
  }
  
  public static void shutdown()
  {
    ChannelFuture cf = serverChannel.close();
    

    Log.printLog("Removing Connections");
    for (;;)
    {
      Iterator<Channel> itr = FactorialServerHandler.channels.iterator();
      if (!itr.hasNext()) {
        break;
      }
      try
      {
        close((Channel)itr.next());
      }
      catch (Exception ex)
      {
        Log.printException("close", ex);
      }
      try
      {
        Thread.sleep(1L);
      }
      catch (Exception localException1) {}
    }
    cf.awaitUninterruptibly(10000L);
    
    bossGroup.shutdownGracefully();
    workerGroup.shutdownGracefully();
    try
    {
      WorkerTasks.RoomsTasks.shutdown();
      WorkerTasks.RoomsTasks.awaitTermination(10L, TimeUnit.SECONDS);
    }
    catch (Exception ex)
    {
      Log.printException("shutdown", ex);
    }
    try
    {
      WorkerTasks.SnowWarTasks.shutdown();
      WorkerTasks.SnowWarTasks.awaitTermination(10L, TimeUnit.SECONDS);
    }
    catch (Exception ex)
    {
      Log.printException("shutdown", ex);
    }
    try
    {
      WorkerTasks.ItemsTasks.shutdown();
      WorkerTasks.ItemsTasks.awaitTermination(10L, TimeUnit.SECONDS);
    }
    catch (Exception ex)
    {
      Log.printException("shutdown", ex);
    }
    try
    {
      WorkerTasks.DatabaseExecTasks.shutdown();
      WorkerTasks.DatabaseExecTasks.awaitTermination(10L, TimeUnit.SECONDS);
    }
    catch (Exception ex)
    {
      Log.printException("shutdown", ex);
    }
    try
    {
      WorkerTasks.DatabaseQueryTasks.shutdown();
      WorkerTasks.DatabaseQueryTasks.awaitTermination(10L, TimeUnit.SECONDS);
    }
    catch (Exception ex)
    {
      Log.printException("shutdown", ex);
    }
    Database.close();
    
    Log.printLog("");
    Log.printLog("Closed.");
  }
}


