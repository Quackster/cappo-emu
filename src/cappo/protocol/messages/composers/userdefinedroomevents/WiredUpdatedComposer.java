package cappo.protocol.messages.composers.userdefinedroomevents;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class WiredUpdatedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter Message = new MessageWriter();
    Composer.initPacket(HEADER, Message);
    Composer.endPacket(Message);
    return Message;
  }
}


