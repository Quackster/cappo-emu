package cappo.protocol.messages.composers.advertisement;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomAdComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String UrlImage, String UrlLink)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(UrlImage, ClientMessage);
    Composer.add(UrlLink, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


