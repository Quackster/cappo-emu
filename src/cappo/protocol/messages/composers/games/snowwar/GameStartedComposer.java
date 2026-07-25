package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.RoomQueue;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeGame2;
import java.util.Map;

public class GameStartedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(RoomQueue queue)
  {
    MessageWriter ClientMessage = new MessageWriter(100 + queue.players.size() * 200);
    Composer.initPacket(HEADER, ClientMessage);
    SerializeGame2.parse(ClientMessage, queue);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


