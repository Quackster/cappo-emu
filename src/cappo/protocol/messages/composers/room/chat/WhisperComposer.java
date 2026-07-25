package cappo.protocol.messages.composers.room.chat;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeSay;
import java.util.List;

public class WhisperComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int virtualId, String message, int gesture, int styleId, List<String> urls, int sayId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    SerializeSay.parse(ClientMessage, virtualId, message, gesture, styleId, urls, sayId);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


