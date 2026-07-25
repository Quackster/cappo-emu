package cappo.protocol.messages.composers.room.chat;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeSay;
import java.util.List;

public class ChatComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int virtualId, String text, int gesture, int styleId, List<String> urls, int sayId)
  {
    MessageWriter clientMessage = new MessageWriter();
    Composer.initPacket(HEADER, clientMessage);
    SerializeSay.parse(clientMessage, virtualId, text, gesture, styleId, urls, sayId);
    Composer.endPacket(clientMessage);
    return clientMessage;
  }
}


