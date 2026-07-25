package cappo.protocol.messages.composers.avatar;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.List;

public class ResultCheckUserNameComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int result, String name, List<String> nicksavailable)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(result), ClientMessage);
    Composer.add(name, ClientMessage);
    Composer.add(Integer.valueOf(nicksavailable.size()), ClientMessage);
    for (String nick : nicksavailable) {
      Composer.add(nick, ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


