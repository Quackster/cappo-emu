package cappo.engine.network;

import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import java.util.List;

public class HabboPacketDecoder
  extends ByteToMessageDecoder
{
  // Policy string matches the known-good format used by the Ion/Deltar
  // emulator (server/trunk/HabboHotel/Client/GameClient.cs): the <!DOCTYPE>
  // declaration and \r\n line breaks follow Adobe's cross-domain-policy spec,
  // and to-ports="1-31111" covers our game port (30001). Null-terminated.
  private static final byte[] crossdomain =
    ("<?xml version=\"1.0\"?>\r\n"
    + "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n"
    + "<cross-domain-policy>\r\n"
    + "<allow-access-from domain=\"*\" to-ports=\"1-31111\" />\r\n"
    + "</cross-domain-policy>" + "\0").getBytes();
  
  protected void decode(ChannelHandlerContext ctx, ByteBuf buff, List<Object> out)
    throws Exception
  {
    try
    {
      Channel ch = ctx.channel();
      if (buff.readableBytes() < 6) {
        return;
      }
      buff.markReaderIndex();
      
      Connection cn = (Connection)ch.attr(FactorialServerHandler.CONNECTION).get();
      
      byte[] packetLen = new byte[4];
      buff.readBytes(packetLen);
      if (cn == null)
      {
        if (packetLen[0] == 60)
        {
          ch.writeAndFlush(crossdomain);

          buff.clear();
        }
      }
      else if (cn.RC4Decode != null)
      {
        cn.RC4Decode.backup();
        cn.RC4Decode.parse(packetLen);
      }
      MessageReader len = new MessageReader(packetLen);
      int bodyLen = len.readInt();
      if (buff.readableBytes() < bodyLen)
      {
        if (cn == null)
        {
          CappoServer.close(ch);
          return;
        }
        if (cn.RC4Decode != null) {
          cn.RC4Decode.restore();
        }
        buff.resetReaderIndex();
        return;
      }
      if (bodyLen < 2)
      {
        buff.clear();
        
        Log.printLog("Bad packet len, bodyLen < 2 " + bodyLen + " " + buff.readableBytes());
        CappoServer.close(ch);
        return;
      }
      out.add(readMessage(buff, cn == null ? null : cn.RC4Decode, bodyLen));
    }
    catch (Exception ex)
    {
      Log.printException("HabboPacketDecoder", ex);
    }
  }
  
  private static MessageReader readMessage(ByteBuf buffer, Crypto crypto, int len)
  {
    byte[] packet = new byte[len];
    buffer.readBytes(packet);
    if (crypto != null) {
      crypto.parse(packet);
    }
    return new MessageReader(packet).setHeaderId();
  }
}


