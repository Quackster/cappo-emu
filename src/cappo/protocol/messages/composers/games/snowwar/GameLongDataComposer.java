package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.RoomQueue;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeGame2;
import java.util.Map;

public class GameLongDataComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(RoomQueue lobby)
  {
    MessageWriter ClientMessage = new MessageWriter(100 + lobby.players.size() * 200);
    Composer.initPacket(HEADER, ClientMessage);
    SerializeGame2.parse(ClientMessage, lobby);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


