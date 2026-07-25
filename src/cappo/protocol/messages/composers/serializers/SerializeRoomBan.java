package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class SerializeRoomBan
{
  public static void parse(MessageWriter ClientMessage, int userId, String userName)
  {
    Composer.add(Integer.valueOf(userId), ClientMessage);
    Composer.add(userName, ClientMessage);
  }
}


