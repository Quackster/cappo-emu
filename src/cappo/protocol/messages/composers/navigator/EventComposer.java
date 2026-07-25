package cappo.protocol.messages.composers.navigator;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class EventComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int eventId, String userName, int roomId, int eventCategory, String eventName, String eventDescription, int eventStartTime)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(eventId), ClientMessage);
    Composer.add(userName, ClientMessage);
    Composer.add(Integer.valueOf(roomId), ClientMessage);
    Composer.add(Integer.valueOf(eventCategory), ClientMessage);
    Composer.add(eventName, ClientMessage);
    Composer.add(eventDescription, ClientMessage);
    Composer.add(Integer.valueOf(eventStartTime), ClientMessage);
    Composer.add(Integer.valueOf(eventStartTime), ClientMessage);
    



    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
  
  public static final MessageWriter compose()
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


