package cappo.protocol.messages.composers.room.chat;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeSay;
import java.util.List;

public class ShoutComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int VirtualId, String Text, int Gesture, int styleId, List<String> Urls, int SayId)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    SerializeSay.parse(ClientMessage, VirtualId, Text, Gesture, styleId, Urls, SayId);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


