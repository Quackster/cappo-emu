package cappo.game.collections;

import cappo.engine.network.QueueWriter;
import cappo.protocol.messages.composers.moderation.ModMessageComposer;
import cappo.protocol.messages.composers.notifications.BroadcastImageComposer;
import cappo.protocol.messages.composers.notifications.HabboBroadcastComposer;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import java.util.Date;
import java.util.Random;

public class Utils
{
  private static Date now = new Date();
  static Random ran = new Random();
  
  public static Date GetDate(long t)
  {
    return new Date(t);
  }
  
  public static Date GetDateNow()
  {
    now.setTime(System.currentTimeMillis());
    return now;
  }
  
  public static int GetRandomNumber(int min, int max)
  {
    if (min < 0)
    {
      min *= -1;
      return (int)(ran.nextDouble() * (max + min)) - min;
    }
    max++;
    
    return (min + (int)(ran.nextDouble() * (max - min))) % max;
  }
  
  public static long getTimestamp()
  {
    return System.currentTimeMillis() / 1000L;
  }
  
  public static boolean isBadInteger(String i)
  {
    try
    {
      Integer.parseInt(i);
      return false;
    }
    catch (NumberFormatException ex) {}
    return true;
  }
  
  public static String convertToHex(byte[] data)
  {
    StringBuilder buf = new StringBuilder(50);
    byte[] arrayOfByte = data;int j = data.length;
    for (int i = 0; i < j; i++)
    {
      byte element = arrayOfByte[i];
      int halfbyte = element >>> 4 & 0xF;
      int two_halfs = 0;
      do
      {
        if ((halfbyte >= 0) && (halfbyte <= 9)) {
          buf.append((char)(48 + halfbyte));
        } else {
          buf.append((char)(97 + (halfbyte - 10)));
        }
        halfbyte = element & 0xF;
      } while (
      





        two_halfs++ < 1);
    }
    return buf.toString();
  }
  
  public static void AlertFromStaffOld(Channel Socket, String Text, String Link)
  {
    QueueWriter.writeAndFlush(Socket, ModMessageComposer.compose(Text, Link));
  }
  
  public static void AlertFromHotel(Channel Socket, String Text)
  {
    QueueWriter.writeAndFlush(Socket, HabboBroadcastComposer.compose(Text));
  }
  
  public static void AlertFromHotel(DefaultChannelGroup group, String Text)
  {
    group.writeAndFlush(HabboBroadcastComposer.compose(Text));
  }
  
/* :0:   */   public static void broadcastImage(Channel socket, String uri)
/* :1:   */   {
/* :2:91 */     QueueWriter.writeAndFlush(socket, BroadcastImageComposer.compose(uri));
/* :3:   */   }
/* :4:   */   
/* :5:   */   public static void broadcastImage(DefaultChannelGroup group, String uri)
/* :6:   */   {
/* :7:95 */     group.writeAndFlush(BroadcastImageComposer.compose(uri));
/* :8:   */   }
/* :9:   */ }


