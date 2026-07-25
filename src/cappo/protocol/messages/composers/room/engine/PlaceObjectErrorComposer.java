package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class PlaceObjectErrorComposer
{
  public static final int CANT_TRADE_STUFF = 1;
  public static final int CANT_SET_ITEM = 11;
  public static final int MAX_STICKIES = 12;
  public static final int MAX_FURNITURE = 20;
  public static final int MAX_PETS = 21;
  public static final int MAX_QUEUETILES = 22;
  public static final int MAX_SOUNDFURNI = 23;
  public static int HEADER;
  
  public static final MessageWriter compose(int errorCode)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(errorCode), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


