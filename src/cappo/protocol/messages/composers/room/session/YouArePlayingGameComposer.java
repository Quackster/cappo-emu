package cappo.protocol.messages.composers.room.session;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class YouArePlayingGameComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(boolean isPlaying)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Boolean.valueOf(isPlaying), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


