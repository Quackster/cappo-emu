package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.settings.PlayerBan;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeRoomBan;
import java.util.Collection;

public class BannedUsersComposer
{
  public static int HEADER;
  
  public static MessageWriter compose(int roomId, Collection<PlayerBan> bans)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(roomId), ClientMessage);
    Composer.add(Integer.valueOf(bans.size()), ClientMessage);
    for (PlayerBan ban : bans) {
      SerializeRoomBan.parse(ClientMessage, ban.player.userId, ban.player.userName);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


