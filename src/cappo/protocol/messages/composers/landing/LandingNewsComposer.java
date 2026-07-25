package cappo.protocol.messages.composers.landing;

import cappo.engine.network.MessageWriter;
import cappo.game.landing.LandingNews;
import cappo.protocol.messages.Composer;
import java.util.List;

public class LandingNewsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(LandingNews.news.size()), ClientMessage);
    for (LandingNews lNew : LandingNews.news)
    {
      Composer.add(Integer.valueOf(lNew.id), ClientMessage);
      Composer.add(lNew.newTitle, ClientMessage);
      Composer.add(lNew.newText, ClientMessage);
      Composer.add(lNew.button, ClientMessage);
      Composer.add(Integer.valueOf(lNew.isClientAction ? 1 : 0), ClientMessage);
      Composer.add(lNew.getLink(), ClientMessage);
      Composer.add(lNew.newImage, ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


