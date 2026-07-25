package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeGameStatus;
import java.util.List;

public class GameStatusComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(SnowWarRoom arena)
  {
    MessageWriter ClientMessage = new MessageWriter(100 + arena.gameEvents.size() * 50);
    Composer.initPacket(HEADER, ClientMessage);
    SerializeGameStatus.parse(ClientMessage, arena, false);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


