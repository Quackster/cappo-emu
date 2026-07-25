package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class NewBuddyRequestComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int RequesterId, String RequesterUserName)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(RequesterId), ClientMessage);
    Composer.add(RequesterUserName, ClientMessage);
    Composer.add(Integer.toString(RequesterId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


