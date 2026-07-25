package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class GameChatFromPlayerComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int UserId, String Text)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(UserId), ClientMessage);
    Composer.add(Text, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


