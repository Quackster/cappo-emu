package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class MessengerErrorComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int MsgId, int ErrorCode)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(MsgId), ClientMessage);
    Composer.add(Integer.valueOf(ErrorCode), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


