package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class SerializeCatalogPageData
{
  public static void parse(MessageWriter ClientMessage, String[][] PageData)
  {
    for (String[] PagData : PageData)
    {
      Composer.add(Integer.valueOf(PagData.length), ClientMessage);
      for (String Data : PagData) {
        Composer.add((Data == null) ? "" : Data, ClientMessage);
      }
    }
  }
}


