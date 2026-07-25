package cappo.protocol.messages.composers.userdefinedroomevents;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class OpenWiredComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int WiredId)
  {
    MessageWriter Message = new MessageWriter();
    Composer.initPacket(HEADER, Message);
    Composer.add(Integer.valueOf(WiredId), Message);
    Composer.endPacket(Message);
    return Message;
  }
}


