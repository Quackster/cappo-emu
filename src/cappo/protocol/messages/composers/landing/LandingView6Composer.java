package cappo.protocol.messages.composers.landing;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class LandingView6Composer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add("africaJungle", ClientMessage);
    Composer.add(Integer.valueOf(2), ClientMessage);
    

    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add("capostrike93", ClientMessage);
    Composer.add("ch-3111-63-62.hd-3103-1.hr-3163-39.lg-285-77.sh-305-78", ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    

    Composer.add(Integer.valueOf(2), ClientMessage);
    Composer.add("Nick", ClientMessage);
    Composer.add("ch-3111-63-62.hd-3103-1.hr-3163-39.lg-285-77.sh-305-78", ClientMessage);
    Composer.add(Integer.valueOf(2), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    

    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


