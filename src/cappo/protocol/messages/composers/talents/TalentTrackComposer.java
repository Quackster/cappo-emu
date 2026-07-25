package cappo.protocol.messages.composers.talents;

import cappo.engine.network.MessageWriter;
import cappo.game.talents.TalentTrack;
import cappo.protocol.messages.Composer;

public class TalentTrackComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(TalentTrack talent)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


