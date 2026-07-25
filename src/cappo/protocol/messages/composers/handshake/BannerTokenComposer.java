package cappo.protocol.messages.composers.handshake;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class BannerTokenComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String Token)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Token, ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


