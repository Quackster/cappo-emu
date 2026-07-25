package cappo.protocol.messages.composers.handshake;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ServerPublicKeyComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String PublicKey)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(PublicKey, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


