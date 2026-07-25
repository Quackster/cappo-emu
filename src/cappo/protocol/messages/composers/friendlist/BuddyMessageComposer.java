package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class BuddyMessageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int SenderId, String Message)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(SenderId), ClientMessage);
    Composer.add(Message, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


