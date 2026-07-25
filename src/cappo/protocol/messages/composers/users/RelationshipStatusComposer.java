package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.Composer;

public class RelationshipStatusComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(PlayerData player)
  {
    MessageWriter message = new MessageWriter();
    Composer.initPacket(HEADER, message);
    Composer.add(Integer.valueOf(player.userId), message);
    player.messenger.serializeRelationshipStatus(message);
    Composer.endPacket(message);
    return message;
  }
}


