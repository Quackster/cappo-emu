package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class FavouriteChangedComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int RoomId, Boolean Add)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(RoomId), ClientMessage);
    Composer.add(Add, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


