package cappo.protocol.messages.composers.games.gamecenter;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class JoinedPlayerQueueComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int GameId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(GameId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


