package cappo.protocol.messages.composers.handshake;

import cappo.engine.network.MessageWriter;
import cappo.engine.settings.PerkAllowance;
import cappo.game.player.data.AvatarData;
import cappo.protocol.messages.Composer;
import java.util.List;

public class PerkAllowancesComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(AvatarData avatarData)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(avatarData.perksAllowances.size()), ClientMessage);
    for (PerkAllowance perk : avatarData.perksAllowances)
    {
      Composer.add(perk.codeName, ClientMessage);
      Composer.add(perk.errorText, ClientMessage);
      Composer.add(Boolean.valueOf(perk.active), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


