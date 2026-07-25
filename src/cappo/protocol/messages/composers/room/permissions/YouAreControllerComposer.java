package cappo.protocol.messages.composers.room.permissions;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class YouAreControllerComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int ControllerLevel)
  {
    MessageWriter ClientMessage = new MessageWriter(10);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ControllerLevel), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


