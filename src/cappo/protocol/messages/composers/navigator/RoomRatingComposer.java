package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomRatingComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int Score, boolean Show)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Score), ClientMessage);
    Composer.add(Boolean.valueOf(Show), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


