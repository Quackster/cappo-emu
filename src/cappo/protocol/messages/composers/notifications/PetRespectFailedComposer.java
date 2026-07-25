package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class PetRespectFailedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int requiredAge, int petAge)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(requiredAge), ClientMessage);
    Composer.add(Integer.valueOf(petAge), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


