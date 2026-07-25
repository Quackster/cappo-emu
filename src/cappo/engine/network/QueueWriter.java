package cappo.engine.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class QueueWriter
{
  public static void writeAndFlush(Channel s, MessageWriter writer)
  {
    s.writeAndFlush(writer);
  }
  
  public static void writeAndClose(Channel s, MessageWriter Message)
  {
    ChannelFuture f = s.writeAndFlush(Message);
    f.awaitUninterruptibly();
    s.close();
  }
  
  public static void write(Channel s, MessageWriter writer)
  {
    s.write(writer);
  }
}



