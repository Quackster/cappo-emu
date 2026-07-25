package cappo.protocol.messages.composers.room.chat;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserTypingComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int virtualId, Boolean isTyping)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(virtualId), ClientMessage);
    Composer.add(Integer.valueOf(isTyping.booleanValue() ? 1 : 0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


