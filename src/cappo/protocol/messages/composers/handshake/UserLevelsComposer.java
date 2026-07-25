package cappo.protocol.messages.composers.handshake;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserLevelsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int ClubLevel, int RankLevel)
  {
    MessageWriter ClientMessage = new MessageWriter(14);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(ClubLevel), ClientMessage);
    Composer.add(Integer.valueOf(RankLevel), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


