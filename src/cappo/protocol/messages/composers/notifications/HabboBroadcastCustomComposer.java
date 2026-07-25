package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.Map;

public class HabboBroadcastCustomComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String notificationKey, Map<String, String> params)
  {
    MessageWriter writer = new MessageWriter();
    Composer.initPacket(HEADER, writer);
    Composer.add(notificationKey, writer);
    if (params == null)
    {
      Composer.writeInt32(0, writer);
    }
    else
    {
      Composer.add(Integer.valueOf(params.size()), writer);
      for (String paramKey : params.keySet())
      {
        String value = (String)params.get(paramKey);
        Composer.add(paramKey, writer);
        Composer.add(value, writer);
      }
    }
    Composer.endPacket(writer);
    return writer;
  }
}


