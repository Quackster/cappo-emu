package cappo.protocol.messages.composers.roomsettings;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.settings.PlayerRight;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeFlatController;
import java.util.Collection;

public class FlatControllersComposer
{
  public static int HEADER;
  
  public static MessageWriter compose(int roomId, Collection<PlayerRight> controllers)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(roomId), ClientMessage);
    Composer.add(Integer.valueOf(controllers.size()), ClientMessage);
    for (PlayerRight right : controllers) {
      SerializeFlatController.parse(ClientMessage, right.player.userId, right.player.userName);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


