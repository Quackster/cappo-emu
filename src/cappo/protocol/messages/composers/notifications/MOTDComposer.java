package cappo.protocol.messages.composers.notifications;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class MOTDComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(String[] Lines)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Lines.length), ClientMessage);
    String[] arrayOfString = Lines;int j = Lines.length;
    for (int i = 0; i < j; i++)
    {
      String line = arrayOfString[i];
      Composer.add(line, ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


