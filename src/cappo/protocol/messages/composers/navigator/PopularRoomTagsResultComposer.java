package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.Map;

public class PopularRoomTagsResultComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Map<String, Integer> PopularTags)
  {
    MessageWriter ClientMessage = new MessageWriter(50 + PopularTags.size() * 30);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(PopularTags.size()), ClientMessage);
    for (String key : PopularTags.keySet())
    {
      Composer.add(key, ClientMessage);
      Composer.add(PopularTags.get(key), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


