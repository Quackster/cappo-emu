package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.Badge;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class UserBadgesComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int ClientId, Collection<Badge> BadgesSelected)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ClientId), ClientMessage);
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


