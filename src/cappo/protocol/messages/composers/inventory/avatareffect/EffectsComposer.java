package cappo.protocol.messages.composers.inventory.avatareffect;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.AvatarEffect;
import cappo.game.collections.Utils;
import cappo.protocol.messages.Composer;
import java.util.List;

public class EffectsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(List<AvatarEffect> Effects)
  {
    MessageWriter ClientMessage = new MessageWriter(100 + Effects.size() * 50);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Effects.size()), ClientMessage);
    for (AvatarEffect Effect : Effects)
    {
      Composer.add(Integer.valueOf(Effect.effectType), ClientMessage);
      Composer.add(Integer.valueOf(Effect.noNamed), ClientMessage);
      Composer.add(Integer.valueOf(Effect.TotalDuration), ClientMessage);
      Composer.add(Integer.valueOf(Effect.Activated ? 1 : 0), ClientMessage);
      int timeLeft = (int)(Effect.TotalDuration - (Utils.getTimestamp() - Effect.ActivateTimestamp));
      Composer.add(Integer.valueOf(timeLeft), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


