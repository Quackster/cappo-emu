package cappo.protocol.messages.composers.inventory.badges;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.Badge;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class BadgesComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<Badge> Badges, Collection<Badge> BadgesSelected)
  {
    MessageWriter ClientMessage = new MessageWriter(500 + Badges.size() * 15);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Badges.size()), ClientMessage);
    for (Badge Badge : Badges)
    {
      Composer.add(Integer.valueOf(Badge.badgeId), ClientMessage);
      Composer.add(Badge.badgeCode, ClientMessage);
    }
    Composer.add(Integer.valueOf(BadgesSelected.size()), ClientMessage);
    for (Badge Badge : BadgesSelected)
    {
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Badge.badgeCode, ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


