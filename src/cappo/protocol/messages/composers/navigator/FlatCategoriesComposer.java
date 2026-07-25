package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.game.navigator.NavigatorCategories;
import cappo.protocol.messages.Composer;
import java.util.Map;

public class FlatCategoriesComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter();
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Integer.valueOf(NavigatorCategories.roomCategories.size()), ClientMessage);
      for (NavigatorCategories cat : NavigatorCategories.roomCategories.values())
      {
        Composer.add(Integer.valueOf(cat.id), ClientMessage);
        Composer.add(cat.caption, ClientMessage);
        Composer.add(Boolean.valueOf(true), ClientMessage);
      }
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


