package cappo.protocol.messages.composers.userdefinedroomevents;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class WiredUpdateFailedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String Error)
  {
    MessageWriter Message = new MessageWriter();
    Composer.initPacket(HEADER, Message);
    Composer.add(Error, Message);
    Composer.endPacket(Message);
    return Message;
  }
}


