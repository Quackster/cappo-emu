package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.List;

public class SerializeSay
{
  public static void parse(MessageWriter ClientMessage, int virtualId, String message, int gesture, int styleId, List<String> urls, int sayId)
  {
    Composer.add(Integer.valueOf(virtualId), ClientMessage);
    Composer.add(message, ClientMessage);
    Composer.add(Integer.valueOf(gesture), ClientMessage);
    Composer.add(Integer.valueOf(styleId), ClientMessage);
    if (urls == null)
    {
      Composer.add(Integer.valueOf(0), ClientMessage);
    }
    else
    {
      Composer.add(Integer.valueOf(urls.size()), ClientMessage);
      for (String Link : urls)
      {
        Composer.add("/link_to?url=" + Link, ClientMessage);
        Composer.add(Link, ClientMessage);
        Composer.add(Boolean.valueOf(true), ClientMessage);
      }
    }
    Composer.add(Integer.valueOf(sayId), ClientMessage);
  }
}


