package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class ApproveNameComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Result, String ValidationInfo)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Result), ClientMessage);
    Composer.add(ValidationInfo, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


