package cappo.engine.network;

import cappo.engine.logging.Log;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class HabboPacketEncoder
  extends MessageToByteEncoder<Object>
{
  protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf buf)
    throws Exception
  {
    if ((msg instanceof MessageWriter)) {
      try
      {
        MessageWriter v = (MessageWriter)msg;
        buf.writeBytes(v.getMessage());
      }
      catch (Exception ex)
      {
        Log.printException("HabboPacketEncoder", ex);
      }
    }
    if ((msg instanceof byte[])) {
      buf.writeBytes((byte[])msg);
    }
  }
}


