package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class NavigatorSettingsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int homeId, int loadId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(homeId), ClientMessage);
    Composer.add(Integer.valueOf(loadId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


