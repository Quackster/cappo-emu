package cappo.protocol.messages.composers.inventory.avatareffect;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class EffectStopedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int EffectId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(EffectId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


