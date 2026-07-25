package cappo.protocol.messages.composers.moderation;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ModMessageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String Text, String Link)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Text, ClientMessage);
    Composer.add(Link, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


