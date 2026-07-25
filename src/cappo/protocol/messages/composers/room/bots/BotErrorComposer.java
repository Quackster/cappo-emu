package cappo.protocol.messages.composers.room.bots;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class BotErrorComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int errorCode)
  {
    MessageWriter clientMessage = new MessageWriter();
    Composer.initPacket(HEADER, clientMessage);
    Composer.add(Integer.valueOf(errorCode), clientMessage);
    Composer.endPacket(clientMessage);
    return clientMessage;
  }
}


