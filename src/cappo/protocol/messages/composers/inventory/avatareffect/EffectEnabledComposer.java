package cappo.protocol.messages.composers.inventory.avatareffect;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class EffectEnabledComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int EffectId, int TotalDuration)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(EffectId), ClientMessage);
    Composer.add(Integer.valueOf(TotalDuration), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


