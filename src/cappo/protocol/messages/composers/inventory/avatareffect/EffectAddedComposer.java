package cappo.protocol.messages.composers.inventory.avatareffect;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.AvatarEffect;
import cappo.protocol.messages.Composer;

public class EffectAddedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(AvatarEffect effect)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(effect.effectType), ClientMessage);
    Composer.add(Integer.valueOf(effect.noNamed), ClientMessage);
    Composer.add(Integer.valueOf(effect.TotalDuration), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


