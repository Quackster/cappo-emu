package cappo.engine.network;
import io.netty.channel.ChannelHandler;

import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

@ChannelHandler.Sharable
public class FactorialServerHandler
  extends ChannelHandlerAdapter
{
  public static final DefaultChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  public static final AttributeKey<Connection> CONNECTION = AttributeKey.valueOf(FactorialServerHandler.class, "FactorialServerHandler.connection");
  private static final ChannelFutureListener remover = new ChannelFutureListener()
  {
    public void operationComplete(ChannelFuture future)
      throws Exception
    {
      Channel ch = future.channel();
      



      Connection cn = (Connection)ch.attr(FactorialServerHandler.CONNECTION).getAndRemove();
      if (cn == null)
      {
        Log.printException("FactorialServerHandler", new Exception("Channel closed without Connection Attribute!!"));
        return;
      }
      try
      {
        cn.channelDisconnected();
      }
      catch (Exception ex)
      {
        Log.printException("disconnect", ex);
      }
    }
  };
  
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception
  {
    ctx.close();
    channelInactive(ctx);
  }

  public void channelActive(ChannelHandlerContext ctx)
    throws Exception
  {
    Log.printLog("connect " + ctx.channel().remoteAddress());
    ctx.fireChannelActive();
  }

  public void channelInactive(ChannelHandlerContext ctx)
    throws Exception
  {
    Channel ch = ctx.channel();
    Connection cn = (Connection)ch.attr(CONNECTION).get();
    Log.printLog("disconnect " + ch.remoteAddress() + (cn != null ? "" : " (pre-handshake)"));
    ctx.fireChannelInactive();
  }
  
  public void channelRead(ChannelHandlerContext ctx, Object packet)
  {
    messageReceived(ctx, (MessageReader)packet);
  }
  
  public void channelReadComplete(ChannelHandlerContext ctx)
  {
    ctx.flush();
  }
  
  private void messageReceived(ChannelHandlerContext ctx, MessageReader reader)
  {
    Channel ch = ctx.channel();
    Connection cn = (Connection)ch.attr(CONNECTION).get();
    if (cn == null)
    {
      if (reader.headerId == 4000)
      {
        if (channels.add(ch))
        {
          cn = new Connection();
          cn.socket = ch;
          

          ch.attr(CONNECTION).set(cn);
          ch.closeFuture().addListener(remover);
        }
        return;
      }
      Log.printException("messageReceived", new Exception("Not have Connection Class"));
      return;
    }
    cn.currentPacket = reader;
    if (IncomingMessageEvent.callBacks[reader.headerId] != null)
    {
      long now = System.currentTimeMillis();
      try
      {
        IncomingMessageEvent.callBacks[reader.headerId].messageReceived(cn);
      }
      catch (Exception ex)
      {
        Log.printException("messageReceived", ex);
      }
      long delay = System.currentTimeMillis() - now;
      if (delay > 1000L) {
        Log.printLog("PacketSlow | id = " + reader.headerId + " | ms = " + delay);
      }
    }
    else
    {
      Log.printLog("Packet desconocido <" + reader.headerId + ">");
    }
  }
}
